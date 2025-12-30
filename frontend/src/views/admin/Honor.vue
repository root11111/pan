<template>
  <el-card>
    <template #header>
      <div style="display: flex; justify-content: space-between;">
        <span>荣誉资质管理</span>
        <el-button type="primary" @click="handleAdd">新增荣誉资质</el-button>
      </div>
    </template>
    <el-table :data="tableData" border>
      <el-table-column prop="title" label="标题" width="200" />
      <el-table-column prop="description" label="描述" show-overflow-tooltip />
      <el-table-column label="图片" width="150">
        <template #default="{ row }">
          <img v-if="row.image" :src="getImageUrl(row.image)" style="width: 120px; height: 90px; object-fit: cover; border-radius: 4px;" />
          <span v-else style="color: #909399;">-</span>
        </template>
      </el-table-column>
      <el-table-column prop="sortOrder" label="排序" width="100" />
      <el-table-column label="操作" width="200">
        <template #default="{ row }">
          <el-button size="small" @click="handleEdit(row)">编辑</el-button>
          <el-button size="small" type="danger" @click="handleDelete(row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="600px">
      <el-form :model="form" label-width="120px">
        <el-form-item label="标题">
          <el-input v-model="form.title" />
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="form.description" type="textarea" :rows="4" />
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
        <el-form-item label="排序">
          <el-input-number v-model="form.sortOrder" :min="0" />
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
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { UploadFilled } from '@element-plus/icons-vue'
import axios from 'axios'
import { getImageUrl } from '../../utils/image'

export default {
  name: 'AdminHonor',
  components: {
    UploadFilled
  },
  setup() {
    const tableData = ref([])
    const dialogVisible = ref(false)
    const dialogTitle = ref('新增荣誉资质')
    const form = ref({
      id: null,
      title: '',
      description: '',
      image: '',
      sortOrder: 0
    })

    const api = axios.create({
      baseURL: import.meta.env.VITE_API_BASE_URL || 'http://localhost:8082/api',
      headers: {
        Authorization: `Bearer ${localStorage.getItem('admin_token')}`
      }
    })

    const uploadUrl = `${import.meta.env.VITE_API_BASE_URL || 'http://localhost:8082/api'}/file/upload`
    const uploadHeaders = {
      Authorization: `Bearer ${localStorage.getItem('admin_token')}`
    }

    const loadData = async () => {
      try {
        const res = await api.get('/admin/honor/list')
        if (res.data.code === 200) {
          tableData.value = res.data.data
        }
      } catch (error) {
        ElMessage.error('加载数据失败')
      }
    }

    const handleAdd = () => {
      dialogTitle.value = '新增荣誉资质'
      form.value = {
        id: null,
        title: '',
        description: '',
        image: '',
        sortOrder: 0
      }
      dialogVisible.value = true
    }

    const handleEdit = (row) => {
      dialogTitle.value = '编辑荣誉资质'
      form.value = { ...row }
      dialogVisible.value = true
    }

    const handleImageSuccess = (response) => {
      if (response.code === 200) {
        form.value.image = response.data
        ElMessage.success('上传成功')
      } else {
        ElMessage.error(response.message || '上传失败')
      }
    }

    const handleReupload = () => {
      form.value.image = ''
    }

    const handleSave = async () => {
      try {
        const res = await api.post('/admin/honor/save', form.value)
        if (res.data.code === 200) {
          ElMessage.success('保存成功')
          dialogVisible.value = false
          loadData()
        } else {
          ElMessage.error(res.data.message || '保存失败')
        }
      } catch (error) {
        ElMessage.error('保存失败')
      }
    }

    const handleDelete = async (id) => {
      try {
        await ElMessageBox.confirm('确定要删除吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
        const res = await api.delete(`/admin/honor/${id}`)
        if (res.data.code === 200) {
          ElMessage.success('删除成功')
          loadData()
        } else {
          ElMessage.error(res.data.message || '删除失败')
        }
      } catch (error) {
        if (error !== 'cancel') {
          ElMessage.error('删除失败')
        }
      }
    }

    onMounted(() => {
      loadData()
    })

    return {
      tableData,
      dialogVisible,
      dialogTitle,
      form,
      uploadUrl,
      uploadHeaders,
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

