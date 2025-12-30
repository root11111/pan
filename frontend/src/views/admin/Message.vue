<template>
  <el-card>
    <template #header>
      <div style="display: flex; justify-content: space-between;">
        <span>留言管理</span>
        <el-radio-group v-model="statusFilter" @change="loadData">
          <el-radio-button :label="null">全部</el-radio-button>
          <el-radio-button :label="0">未处理</el-radio-button>
          <el-radio-button :label="1">已处理</el-radio-button>
        </el-radio-group>
      </div>
    </template>
    <el-table :data="tableData" border>
      <el-table-column prop="name" label="姓名" width="120" />
      <el-table-column prop="phone" label="电话" width="150" />
      <el-table-column prop="email" label="邮箱" width="200" />
      <el-table-column prop="company" label="公司" width="200" />
      <el-table-column prop="content" label="留言内容" show-overflow-tooltip />
      <el-table-column prop="status" label="状态" width="100">
        <template #default="{ row }">
          <el-tag :type="row.status === 1 ? 'success' : 'warning'">
            {{ row.status === 1 ? '已处理' : '未处理' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="提交时间" width="180" />
      <el-table-column label="操作" width="200">
        <template #default="{ row }">
          <el-button
            size="small"
            :type="row.status === 0 ? 'success' : 'info'"
            @click="handleUpdateStatus(row)"
          >
            {{ row.status === 0 ? '标记已处理' : '标记未处理' }}
          </el-button>
          <el-button size="small" type="danger" @click="handleDelete(row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      v-model:current-page="currentPage"
      v-model:page-size="pageSize"
      :total="total"
      layout="total, prev, pager, next"
      @current-change="loadData"
      style="margin-top: 20px;"
    />
  </el-card>
</template>

<script>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getMessagePage, updateMessageStatus, deleteMessage } from '../../api/admin'

export default {
  name: 'AdminMessage',
  setup() {
    const tableData = ref([])
    const currentPage = ref(1)
    const pageSize = ref(10)
    const total = ref(0)
    const statusFilter = ref(null)

    const loadData = async () => {
      try {
        const res = await getMessagePage(currentPage.value, pageSize.value, statusFilter.value)
        if (res.code === 200) {
          tableData.value = res.data.records
          total.value = res.data.total
        }
      } catch (error) {
        ElMessage.error('加载数据失败')
      }
    }

    onMounted(() => {
      loadData()
    })

    const handleUpdateStatus = async (row) => {
      const newStatus = row.status === 0 ? 1 : 0
      try {
        const res = await updateMessageStatus(row.id, newStatus)
        if (res.code === 200) {
          ElMessage.success('更新成功')
          loadData()
        }
      } catch (error) {
        ElMessage.error('更新失败')
      }
    }

    const handleDelete = (id) => {
      ElMessageBox.confirm('确定要删除吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        try {
          const res = await deleteMessage(id)
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
      statusFilter,
      loadData,
      handleUpdateStatus,
      handleDelete
    }
  }
}
</script>

