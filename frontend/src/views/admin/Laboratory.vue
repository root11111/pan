<template>
  <el-card>
    <template #header>
      <div style="display: flex; justify-content: space-between;">
        <span>实验室管理</span>
        <el-button type="primary" @click="handleAdd">新增实验室</el-button>
      </div>
    </template>
    <el-table :data="tableData" border>
      <el-table-column prop="nameCn" label="中文名称" />
      <el-table-column prop="nameEn" label="英文名称" />
      <el-table-column prop="type" label="类型" />
      <el-table-column label="图片">
        <template #default="{ row }">
          <img v-if="row.image" :src="getImageUrl(row.image)" style="width: 80px; height: 60px; object-fit: cover; border-radius: 4px;" />
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200">
        <template #default="{ row }">
          <el-button size="small" @click="handleEdit(row)">编辑</el-button>
          <el-button size="small" type="danger" @click="handleDelete(row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="600px">
      <el-form :model="form" label-width="120px">
        <el-form-item label="中文名称">
          <el-input v-model="form.nameCn" />
        </el-form-item>
        <el-form-item label="英文名称">
          <el-input v-model="form.nameEn" />
        </el-form-item>
        <el-form-item label="类型">
          <el-select v-model="form.type">
            <el-option label="EMC&无线实验室" value="EMC&无线实验室" />
            <el-option label="安规实验室" value="安规实验室" />
            <el-option label="电池实验室" value="电池实验室" />
            <el-option label="化学实验室" value="化学实验室" />
          </el-select>
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="form.descriptionCn" type="textarea" />
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
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { UploadFilled } from '@element-plus/icons-vue'
import { getLaboratories, saveLaboratory, deleteLaboratory } from '../../api/admin'
import { getImageUrl, processImageUrls } from '../../utils/image'

export default {
  name: 'AdminLaboratory',
  components: {
    UploadFilled
  },
  setup() {
    const tableData = ref([])
    const dialogVisible = ref(false)
    const dialogTitle = ref('新增实验室')
    const form = reactive({
      id: null,
      nameCn: '',
      nameEn: '',
      type: '',
      descriptionCn: '',
      image: '',
      sortOrder: 0
    })

    const uploadUrl = `${import.meta.env.VITE_API_BASE_URL || (import.meta.env.DEV ? 'http://localhost:8082/api' : '/api')}/file/upload`
    const uploadHeaders = {
      Authorization: `Bearer ${localStorage.getItem('admin_token')}`
    }

    const loadData = async () => {
      try {
        const res = await getLaboratories()
        if (res.code === 200) {
          tableData.value = processImageUrls(res.data, ['image'])
        }
      } catch (error) {
        ElMessage.error('加载数据失败')
      }
    }

    onMounted(() => {
      loadData()
    })

    const handleAdd = () => {
      dialogTitle.value = '新增实验室'
      Object.assign(form, {
        id: null,
        nameCn: '',
        nameEn: '',
        type: '',
        descriptionCn: '',
        image: '',
        sortOrder: 0
      })
      dialogVisible.value = true
    }

    const handleEdit = (row) => {
      dialogTitle.value = '编辑实验室'
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
        const res = await saveLaboratory(form)
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
          const res = await deleteLaboratory(id)
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

