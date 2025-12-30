<template>
  <el-card>
    <template #header>
      <div style="display: flex; justify-content: space-between; align-items: center;">
        <span>认证分类管理</span>
        <div>
          <el-button type="danger" :disabled="selectedIds.length === 0" @click="handleBatchDelete" style="margin-right: 10px;">
            批量删除 ({{ selectedIds.length }})
          </el-button>
          <el-button type="primary" @click="handleAdd">新增分类</el-button>
        </div>
      </div>
    </template>
    
    <div style="border: 1px solid #dcdfe6; border-radius: 4px; padding: 10px; background: #fff;">
      <el-tree
        :data="treeData"
        :props="{ children: 'children', label: 'nameCn' }"
        show-checkbox
        node-key="id"
        default-expand-all
        :check-strictly="false"
        @check="handleTreeCheck"
        ref="treeRef"
      >
        <template #default="{ node, data }">
          <div class="tree-node">
            <span class="tree-node-label">
              <span style="font-weight: 500;">{{ data.nameCn }}</span>
              <span v-if="data.nameEn" style="color: #909399; margin-left: 10px; font-size: 12px;">
                ({{ data.nameEn }})
              </span>
              <el-tag size="small" style="margin-left: 10px;">排序: {{ data.sortOrder || 0 }}</el-tag>
            </span>
            <span class="tree-node-actions">
              <el-button size="small" type="primary" text @click.stop="handleAddChild(data)">
                添加子分类
              </el-button>
              <el-button size="small" type="primary" text @click.stop="handleEdit(data)">
                编辑
              </el-button>
              <el-button size="small" type="danger" text @click.stop="handleDelete(data.id)">
                删除
              </el-button>
            </span>
          </div>
        </template>
      </el-tree>
    </div>

    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="600px">
      <el-form :model="form" label-width="120px">
        <el-form-item label="中文名称">
          <el-input v-model="form.nameCn" placeholder="请输入中文名称" />
        </el-form-item>
        <el-form-item label="英文名称">
          <el-input v-model="form.nameEn" placeholder="请输入英文名称" />
        </el-form-item>
        <el-form-item label="父分类">
          <el-select v-model="form.parentId" placeholder="选择父分类（留空为顶级分类）" clearable style="width: 100%;">
            <el-option label="顶级分类" :value="0" />
            <el-option 
              v-for="cat in topCategories" 
              :key="cat.id" 
              :label="cat.nameCn" 
              :value="cat.id"
              :disabled="cat.id === form.id"
            />
          </el-select>
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
import axios from 'axios'

export default {
  name: 'AdminCertificationCategory',
  setup() {
    const tableData = ref([])
    const treeData = ref([])
    const topCategories = ref([])
    const dialogVisible = ref(false)
    const dialogTitle = ref('新增分类')
    const selectedIds = ref([])
    const treeRef = ref(null)
    const form = reactive({
      id: null,
      nameCn: '',
      nameEn: '',
      parentId: 0,
      sortOrder: 0
    })

    const api = axios.create({
      baseURL: import.meta.env.VITE_API_BASE_URL || 'http://localhost:8082/api',
      headers: {
        Authorization: `Bearer ${localStorage.getItem('admin_token')}`
      }
    })

    // 将扁平数据转换为树形结构
    const buildTree = (data) => {
      const map = {}
      const roots = []
      
      // 创建映射
      data.forEach(item => {
        map[item.id] = { ...item, children: [] }
      })
      
      // 构建树形结构
      data.forEach(item => {
        const node = map[item.id]
        if (item.parentId && item.parentId !== 0 && map[item.parentId]) {
          map[item.parentId].children.push(node)
        } else {
          roots.push(node)
        }
      })
      
      // 排序
      const sortNodes = (nodes) => {
        nodes.sort((a, b) => (a.sortOrder || 0) - (b.sortOrder || 0))
        nodes.forEach(node => {
          if (node.children && node.children.length > 0) {
            sortNodes(node.children)
          }
        })
        return nodes
      }
      
      return sortNodes(roots)
    }

    const loadData = async () => {
      try {
        const res = await api.get('/admin/certification-category/list')
        if (res.data.code === 200) {
          tableData.value = res.data.data
          // 转换为树形结构
          treeData.value = buildTree(res.data.data)
        }
        const topRes = await api.get('/admin/certification-category/top')
        if (topRes.data.code === 200) {
          topCategories.value = topRes.data.data
        }
      } catch (error) {
        ElMessage.error('加载数据失败')
      }
    }

    onMounted(() => {
      loadData()
    })

    const handleAdd = () => {
      dialogTitle.value = '新增分类'
      Object.assign(form, {
        id: null,
        nameCn: '',
        nameEn: '',
        parentId: 0,
        sortOrder: 0
      })
      dialogVisible.value = true
    }

    const handleAddChild = (parent) => {
      dialogTitle.value = '新增子分类'
      Object.assign(form, {
        id: null,
        nameCn: '',
        nameEn: '',
        parentId: parent.id,
        sortOrder: 0
      })
      dialogVisible.value = true
    }

    const handleEdit = (row) => {
      dialogTitle.value = '编辑分类'
      Object.assign(form, { ...row })
      if (!form.parentId) {
        form.parentId = 0
      }
      dialogVisible.value = true
    }

    const handleSave = async () => {
      if (!form.nameCn) {
        ElMessage.warning('请输入中文名称')
        return
      }
      try {
        const res = await api.post('/admin/certification-category/save', form)
        if (res.data.code === 200) {
          ElMessage.success('保存成功')
          dialogVisible.value = false
          loadData()
        }
      } catch (error) {
        ElMessage.error('保存失败')
      }
    }

    const handleTreeCheck = (data, checked) => {
      // 获取所有选中的节点
      const checkedKeys = treeRef.value.getCheckedKeys()
      const halfCheckedKeys = treeRef.value.getHalfCheckedKeys()
      // 合并选中的节点（包括半选中的父节点）
      selectedIds.value = [...checkedKeys, ...halfCheckedKeys]
    }

    const handleBatchDelete = () => {
      if (selectedIds.value.length === 0) {
        ElMessage.warning('请选择要删除的项目')
        return
      }
      
      // 检查是否有选中的分类包含子分类
      const categoriesWithChildren = selectedIds.value.filter(id => {
        return tableData.value.some(cat => cat.parentId === id)
      })
      
      let message = `确定要删除选中的 ${selectedIds.value.length} 项吗？`
      if (categoriesWithChildren.length > 0) {
        message = `确定要删除选中的 ${selectedIds.value.length} 项吗？\n其中有 ${categoriesWithChildren.length} 个分类包含子分类，删除后将同时删除所有子分类。`
      }
      
      ElMessageBox.confirm(message, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        dangerouslyUseHTMLString: false
      }).then(async () => {
        try {
          const res = await api.delete('/admin/certification-category/batch', { data: selectedIds.value })
          if (res.data.code === 200) {
            ElMessage.success('批量删除成功')
            selectedIds.value = []
            loadData()
          } else {
            ElMessage.error(res.data.message || '批量删除失败')
          }
        } catch (error) {
          ElMessage.error(error.response?.data?.message || '批量删除失败')
        }
      })
    }

    const handleDelete = (id) => {
      // 查找要删除的分类
      const category = tableData.value.find(cat => cat.id === id)
      // 检查是否有子分类
      const hasChildren = tableData.value.some(cat => cat.parentId === id)
      
      let message = '确定要删除该分类吗？'
      if (hasChildren) {
        const childrenCount = tableData.value.filter(cat => cat.parentId === id).length
        message = `确定要删除分类"${category?.nameCn || ''}"吗？\n该分类下有 ${childrenCount} 个子分类，删除后将同时删除所有子分类。`
      }
      
      ElMessageBox.confirm(message, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        dangerouslyUseHTMLString: false
      }).then(async () => {
        try {
          const res = await api.delete(`/admin/certification-category/${id}`)
          if (res.data.code === 200) {
            ElMessage.success('删除成功')
            loadData()
          } else {
            ElMessage.error(res.data.message || '删除失败')
          }
        } catch (error) {
          ElMessage.error(error.response?.data?.message || '删除失败')
        }
      })
    }

    return {
      tableData,
      treeData,
      topCategories,
      dialogVisible,
      dialogTitle,
      selectedIds,
      treeRef,
      form,
      handleAdd,
      handleAddChild,
      handleEdit,
      handleSave,
      handleDelete,
      handleTreeCheck,
      handleBatchDelete,
      loadData
    }
  }
}
</script>

<style scoped>
.tree-node {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;
  padding-right: 8px;
}

.tree-node-label {
  flex: 1;
  display: flex;
  align-items: center;
}

.tree-node-actions {
  display: flex;
  gap: 5px;
  margin-left: 20px;
}

:deep(.el-tree-node__content) {
  height: 40px;
  padding: 5px 0;
}

:deep(.el-tree-node__content:hover) {
  background-color: #f5f7fa;
}

:deep(.el-checkbox) {
  margin-right: 8px;
}
</style>

