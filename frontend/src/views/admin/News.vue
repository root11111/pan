<template>
  <el-card>
    <template #header>
      <div style="display: flex; justify-content: space-between;">
        <span>新闻管理</span>
        <div>
          <el-input
            v-model="keyword"
            placeholder="搜索标题"
            style="width: 200px; margin-right: 10px;"
            @keyup.enter="loadData"
          />
          <el-button 
            type="danger" 
            :disabled="selectedIds.length === 0" 
            @click="handleBatchDelete"
            style="margin-right: 10px;"
          >
            批量删除 ({{ selectedIds.length }})
          </el-button>
          <el-button type="primary" @click="handleAdd">新增新闻</el-button>
        </div>
      </div>
    </template>
    <el-table :data="tableData" border @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" />
      <el-table-column prop="title" label="标题" />
      <el-table-column prop="summary" label="摘要" show-overflow-tooltip />
      <el-table-column label="图片" width="100">
        <template #default="{ row }">
          <img v-if="row.image" :src="getImageUrl(row.image)" style="width: 80px; height: 60px; object-fit: cover; border-radius: 4px;" />
          <span v-else style="color: #909399;">-</span>
        </template>
      </el-table-column>
      <el-table-column prop="publishTime" label="发布时间" width="180" />
      <el-table-column prop="viewCount" label="浏览次数" width="100" />
      <el-table-column label="操作" width="200">
        <template #default="{ row }">
          <el-button size="small" @click="handleEdit(row)">编辑</el-button>
          <el-button size="small" type="danger" @click="handleDelete(row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      v-model:current-page="currentPage"
      v-model:page-size="pageSize"
      :total="total"
      :page-sizes="[10, 20, 50, 100, 200, 500]"
      layout="total, sizes, prev, pager, next"
      @current-change="loadData"
      @size-change="handleSizeChange"
      style="margin-top: 20px; justify-content: flex-end;"
    />

    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="800px">
      <el-form :model="form" label-width="120px">
        <el-form-item label="标题">
          <el-input v-model="form.title" />
        </el-form-item>
        <el-form-item label="摘要">
          <el-input v-model="form.summary" type="textarea" :rows="2" />
        </el-form-item>
        <el-form-item label="内容">
          <el-input v-model="form.content" type="textarea" :rows="10" />
        </el-form-item>
        <el-form-item label="图片">
          <el-upload
            :action="uploadUrl"
            :headers="uploadHeaders"
            :on-success="handleImageSuccess"
            :show-file-list="false"
            drag
            accept="image/*"
          >
            <el-icon v-if="!form.image" class="el-icon--upload"><upload-filled /></el-icon>
            <div v-if="!form.image" class="el-upload__text">
              将图片拖到此处，或<em>点击上传</em>
            </div>
            <div v-if="form.image" style="text-align: center;">
              <img :src="getImageUrl(form.image)" style="max-width: 300px; max-height: 300px; border-radius: 4px;" />
              <div style="margin-top: 10px;">
                <el-button size="small" type="primary" @click.stop="handleReupload">重新上传</el-button>
                <el-button size="small" type="danger" @click.stop="form.image = ''">删除</el-button>
              </div>
            </div>
            <template #tip>
              <div class="el-upload__tip" v-if="!form.image">
                支持拖拽上传，只能上传图片文件
              </div>
            </template>
          </el-upload>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSave">保存</el-button>
      </template>
    </el-dialog>
  </el-card>
</template>

<script>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { UploadFilled } from '@element-plus/icons-vue'
import { getNewsPage, saveNews, deleteNews } from '../../api/admin'
import { getImageUrl, processImageUrls } from '../../utils/image'
import api from '../../api/index'

export default {
  name: 'AdminNews',
  components: {
    UploadFilled
  },
  setup() {
    const tableData = ref([])
    const currentPage = ref(1)
    const pageSize = ref(10)
    const total = ref(0)
    const keyword = ref('')
    const selectedIds = ref([])
    const dialogVisible = ref(false)
    const dialogTitle = ref('新增新闻')
    const form = reactive({
      id: null,
      title: '',
      summary: '',
      content: '',
      image: ''
    })

    const uploadUrl = `${import.meta.env.VITE_API_BASE_URL || (import.meta.env.DEV ? 'http://localhost:8082/api' : '/api')}/file/upload`
    const uploadHeaders = {
      Authorization: `Bearer ${localStorage.getItem('admin_token')}`
    }

    const loadData = async () => {
      try {
        const res = await getNewsPage(currentPage.value, pageSize.value, keyword.value)
        if (res.code === 200) {
          tableData.value = processImageUrls(res.data.records, ['image'])
          total.value = res.data.total
        }
      } catch (error) {
        ElMessage.error('加载数据失败')
      }
    }

    const handleSizeChange = (size) => {
      pageSize.value = size
      currentPage.value = 1
      loadData()
    }

    const handleSelectionChange = (selection) => {
      selectedIds.value = selection.map(item => item.id)
    }

    const handleBatchDelete = () => {
      if (selectedIds.value.length === 0) {
        ElMessage.warning('请选择要删除的项目')
        return
      }
      ElMessageBox.confirm(`确定要删除选中的 ${selectedIds.value.length} 条新闻吗？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        try {
          const res = await api.delete('/admin/news/batch', { data: selectedIds.value })
          if (res.data.code === 200) {
            ElMessage.success('批量删除成功')
            selectedIds.value = []
            loadData()
          } else {
            ElMessage.error(res.data.message || '批量删除失败')
          }
        } catch (error) {
          ElMessage.error('批量删除失败')
        }
      })
    }

    onMounted(() => {
      loadData()
    })

    const handleAdd = () => {
      dialogTitle.value = '新增新闻'
      Object.assign(form, {
        id: null,
        title: '',
        summary: '',
        content: '',
        image: ''
      })
      dialogVisible.value = true
    }

    const handleEdit = (row) => {
      dialogTitle.value = '编辑新闻'
      Object.assign(form, row)
      dialogVisible.value = true
    }

    const handleImageSuccess = (response) => {
      if (response.code === 200) {
        form.image = response.data
        ElMessage.success('上传成功')
      }
    }

    const handleReupload = () => {
      form.image = ''
    }

    const handleSave = async () => {
      try {
        const res = await saveNews(form)
        if (res.code === 200) {
          ElMessage.success('保存成功')
          dialogVisible.value = false
          loadData()
        }
      } catch (error) {
        ElMessage.error('保存失败')
      }
    }

    const handleDelete = (id) => {
      ElMessageBox.confirm('确定要删除吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        try {
          const res = await deleteNews(id)
          if (res.code === 200) {
            ElMessage.success('删除成功')
            loadData()
          }
        } catch (error) {
          ElMessage.error('删除失败')
        }
      })
    }

    return {
      tableData,
      currentPage,
      pageSize,
      total,
      keyword,
      selectedIds,
      dialogVisible,
      dialogTitle,
      form,
      uploadUrl,
      uploadHeaders,
      loadData,
      handleSizeChange,
      handleSelectionChange,
      handleBatchDelete,
      handleAdd,
      handleEdit,
      handleImageSuccess,
      handleReupload,
      handleSave,
      handleDelete,
      getImageUrl
    }
  }
}
</script>

