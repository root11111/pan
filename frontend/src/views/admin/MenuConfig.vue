<template>
  <el-card>
    <template #header>
      <div style="display: flex; justify-content: space-between;">
        <span>菜单配置管理</span>
        <el-button type="primary" @click="handleAdd">新增菜单</el-button>
      </div>
    </template>
    <el-table :data="tableData" border>
      <el-table-column prop="menuKey" label="菜单键值" width="150" />
      <el-table-column prop="menuNameCn" label="中文名称" width="150" />
      <el-table-column prop="menuNameEn" label="英文名称" width="200" />
      <el-table-column label="启用状态" width="100">
        <template #default="{ row }">
          <el-tag :type="row.enabled ? 'success' : 'danger'">
            {{ row.enabled ? '启用' : '禁用' }}
          </el-tag>
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
        <el-form-item label="菜单键值">
          <el-input v-model="form.menuKey" :disabled="!!form.id" placeholder="如：honor, certificate" />
        </el-form-item>
        <el-form-item label="中文名称">
          <el-input v-model="form.menuNameCn" />
        </el-form-item>
        <el-form-item label="英文名称">
          <el-input v-model="form.menuNameEn" />
        </el-form-item>
        <el-form-item label="启用状态">
          <el-switch v-model="form.enabled" />
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
  name: 'AdminMenuConfig',
  setup() {
    const tableData = ref([])
    const dialogVisible = ref(false)
    const dialogTitle = ref('新增菜单')
    const form = ref({
      id: null,
      menuKey: '',
      menuNameCn: '',
      menuNameEn: '',
      enabled: true,
      sortOrder: 0
    })

    const api = axios.create({
      baseURL: import.meta.env.VITE_API_BASE_URL || 'http://localhost:8082/api',
      headers: {
        Authorization: `Bearer ${localStorage.getItem('admin_token')}`
      }
    })

    const loadData = async () => {
      try {
        const res = await api.get('/admin/menu-config/list')
        if (res.data.code === 200) {
          tableData.value = res.data.data
        }
      } catch (error) {
        ElMessage.error('加载数据失败')
      }
    }

    const handleAdd = () => {
      dialogTitle.value = '新增菜单'
      form.value = {
        id: null,
        menuKey: '',
        menuNameCn: '',
        menuNameEn: '',
        enabled: true,
        sortOrder: 0
      }
      dialogVisible.value = true
    }

    const handleEdit = (row) => {
      dialogTitle.value = '编辑菜单'
      form.value = { ...row }
      dialogVisible.value = true
    }

    const handleSave = async () => {
      try {
        const res = await api.post('/admin/menu-config/save', form.value)
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
        const res = await api.delete(`/admin/menu-config/${id}`)
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

