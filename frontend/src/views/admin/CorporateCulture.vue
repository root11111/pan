<template>
  <el-card>
    <template #header>
      <div style="display: flex; justify-content: space-between;">
        <span>企业文化管理</span>
        <el-button type="primary" @click="handleAdd">新增企业文化</el-button>
      </div>
    </template>
    <el-table :data="tableData" border>
      <el-table-column prop="title" label="标题" width="200" />
      <el-table-column prop="description" label="描述" show-overflow-tooltip />
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
import axios from 'axios'

export default {
  name: 'AdminCorporateCulture',
  setup() {
    const tableData = ref([])
    const dialogVisible = ref(false)
    const dialogTitle = ref('新增企业文化')
    const form = ref({
      id: null,
      title: '',
      description: '',
      sortOrder: 0
    })

    const api = axios.create({
      baseURL: import.meta.env.VITE_API_BASE_URL || (import.meta.env.DEV ? 'http://localhost:8082/api' : '/api'),
      headers: {
        Authorization: `Bearer ${localStorage.getItem('admin_token')}`
      }
    })

    const loadData = async () => {
      try {
        const res = await api.get('/admin/corporate-culture/list')
        if (res.data.code === 200) {
          tableData.value = res.data.data
        }
      } catch (error) {
        ElMessage.error('加载数据失败')
      }
    }

    const handleAdd = () => {
      dialogTitle.value = '新增企业文化'
      form.value = {
        id: null,
        title: '',
        description: '',
        sortOrder: 0
      }
      dialogVisible.value = true
    }

    const handleEdit = (row) => {
      dialogTitle.value = '编辑企业文化'
      form.value = { ...row }
      dialogVisible.value = true
    }

    const handleSave = async () => {
      try {
        const res = await api.post('/admin/corporate-culture/save', form.value)
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
        const res = await api.delete(`/admin/corporate-culture/${id}`)
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
      handleAdd,
      handleEdit,
      handleSave,
      handleDelete
    }
  }
}
</script>

