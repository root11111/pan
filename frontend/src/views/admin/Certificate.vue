<template>
  <el-card>
    <template #header>
      <div style="display: flex; justify-content: space-between; align-items: center;">
        <span>证书管理</span>
        <div>
          <el-input
            v-model="keyword"
            placeholder="搜索证书编号或产品名称"
            style="width: 250px; margin-right: 10px;"
            @keyup.enter="loadData"
            clearable
          />
          <el-button type="primary" @click="handleAdd">新增证书</el-button>
        </div>
      </div>
    </template>
    <el-table :data="tableData" border>
      <el-table-column prop="certificateNo" label="证书编号" width="180" />
      <el-table-column prop="productName" label="产品名称" width="200" />
      <el-table-column prop="certificateType" label="证书类型" width="120" />
      <el-table-column prop="issueDate" label="颁发日期" width="120" />
      <el-table-column prop="expiryDate" label="有效期至" width="120" />
      <el-table-column label="状态" width="100">
        <template #default="{ row }">
          <el-tag :type="row.status === '有效' ? 'success' : 'danger'">
            {{ row.status }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="issuer" label="颁发机构" width="150" show-overflow-tooltip />
      <el-table-column label="证书文件" width="100">
        <template #default="{ row }">
          <el-button v-if="row.certificateFile" size="small" link @click="viewFile(row.certificateFile)">
            查看
          </el-button>
          <span v-else style="color: #909399;">-</span>
        </template>
      </el-table-column>
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
      layout="total, prev, pager, next"
      @current-change="loadData"
      style="margin-top: 20px;"
    />

    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="800px">
      <el-form :model="form" label-width="120px">
        <el-form-item label="证书编号" required>
          <el-input v-model="form.certificateNo" />
        </el-form-item>
        <el-form-item label="产品名称" required>
          <el-input v-model="form.productName" />
        </el-form-item>
        <el-form-item label="证书类型">
          <el-input v-model="form.certificateType" />
        </el-form-item>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="颁发日期">
              <el-date-picker
                v-model="form.issueDate"
                type="date"
                value-format="YYYY-MM-DD"
                style="width: 100%;"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="有效期至">
              <el-date-picker
                v-model="form.expiryDate"
                type="date"
                value-format="YYYY-MM-DD"
                style="width: 100%;"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="颁发机构">
          <el-input v-model="form.issuer" />
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="form.description" type="textarea" :rows="4" />
        </el-form-item>
        <el-form-item label="证书文件">
          <el-upload
            :action="uploadUrl"
            :headers="uploadHeaders"
            :on-success="handleFileSuccess"
            :show-file-list="false"
            drag
            accept="image/*,.pdf"
          >
            <el-icon v-if="!form.certificateFile" class="el-icon--upload"><upload-filled /></el-icon>
            <div v-if="!form.certificateFile" class="el-upload__text">
              将证书文件拖到此处，或<em>点击上传</em>
            </div>
            <div v-if="form.certificateFile" style="text-align: center;">
              <!-- 图片预览 -->
              <img 
                v-if="isImageFile(form.certificateFile)" 
                :src="getImageUrl(form.certificateFile)" 
                style="max-width: 300px; max-height: 300px; border-radius: 4px;" 
                @error="handleImageError"
              />
              <!-- PDF预览 -->
              <div v-else-if="isPdfFile(form.certificateFile)" style="padding: 20px; background: #f5f7fa; border-radius: 4px;">
                <el-icon :size="48" style="color: #409eff;"><Document /></el-icon>
                <div style="margin-top: 10px; color: #606266;">PDF文件已上传</div>
                <div style="font-size: 12px; color: #909399; margin-top: 5px;">{{ getFileName(form.certificateFile) }}</div>
              </div>
              <div style="margin-top: 10px;">
                <el-button size="small" type="primary" @click.stop="handleReupload">重新上传</el-button>
                <el-button size="small" type="danger" @click.stop="form.certificateFile = ''">删除</el-button>
              </div>
            </div>
            <template #tip>
              <div class="el-upload__tip" v-if="!form.certificateFile">
                支持拖拽上传，支持图片文件（jpg、png等）和PDF文件
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

    <!-- 文件查看对话框 -->
    <el-dialog v-model="fileDialogVisible" title="查看证书文件" width="900px">
      <!-- 图片显示 -->
      <img 
        v-if="viewFileUrl && isImageFile(viewFileUrl)" 
        :src="viewFileUrl" 
        style="width: 100%; max-height: 70vh; object-fit: contain;" 
        @error="handleImageError"
      />
      <!-- PDF显示 -->
      <iframe 
        v-else-if="viewFileUrl && isPdfFile(viewFileUrl)" 
        :src="viewFileUrl" 
        style="width: 100%; height: 70vh; border: none;"
      ></iframe>
      <!-- 下载按钮 -->
      <div style="text-align: center; margin-top: 20px;" v-if="viewFileUrl">
        <el-button type="primary" @click="downloadFile(viewFileUrl)">
          <el-icon><Download /></el-icon>
          下载文件
        </el-button>
      </div>
    </el-dialog>
  </el-card>
</template>

<script>
import { ref, onMounted, computed } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { UploadFilled, Document, Download } from '@element-plus/icons-vue'
import axios from 'axios'
import { getImageUrl } from '../../utils/image'

export default {
  name: 'AdminCertificate',
  components: {
    UploadFilled,
    Document,
    Download
  },
  setup() {
    const tableData = ref([])
    const currentPage = ref(1)
    const pageSize = ref(10)
    const total = ref(0)
    const keyword = ref('')
    const dialogVisible = ref(false)
    const dialogTitle = ref('新增证书')
    const fileDialogVisible = ref(false)
    const viewFileUrl = ref('')
    const form = ref({
      id: null,
      certificateNo: '',
      productName: '',
      certificateType: '',
      issueDate: null,
      expiryDate: null,
      issuer: '',
      description: '',
      certificateFile: '',
      sortOrder: 0
    })

    const api = axios.create({
      baseURL: import.meta.env.VITE_API_BASE_URL || (import.meta.env.DEV ? 'http://localhost:8082/api' : '/api'),
      headers: {
        Authorization: `Bearer ${localStorage.getItem('admin_token')}`
      }
    })

    const uploadUrl = `${import.meta.env.VITE_API_BASE_URL || (import.meta.env.DEV ? 'http://localhost:8082/api' : '/api')}/admin/certificate/upload`
    const uploadHeaders = {
      Authorization: `Bearer ${localStorage.getItem('admin_token')}`
    }

    const loadData = async () => {
      try {
        const params = {
          current: currentPage.value,
          size: pageSize.value
        }
        if (keyword.value) {
          // 判断是证书编号还是产品名称
          if (/^[A-Z0-9-]+$/i.test(keyword.value)) {
            params.certificateNo = keyword.value
          } else {
            params.productName = keyword.value
          }
        }
        const res = await api.get('/admin/certificate/page', { params })
        if (res.data.code === 200) {
          tableData.value = res.data.data.records
          total.value = res.data.data.total
        }
      } catch (error) {
        ElMessage.error('加载数据失败')
      }
    }

    const handleAdd = () => {
      dialogTitle.value = '新增证书'
      form.value = {
        id: null,
        certificateNo: '',
        productName: '',
        certificateType: '',
        issueDate: null,
        expiryDate: null,
        issuer: '',
        description: '',
        certificateFile: '',
        sortOrder: 0
      }
      dialogVisible.value = true
    }

    const handleEdit = (row) => {
      dialogTitle.value = '编辑证书'
      form.value = { ...row }
      dialogVisible.value = true
    }

    const handleFileSuccess = (response) => {
      if (response.code === 200) {
        form.value.certificateFile = response.data
        ElMessage.success('上传成功')
      } else {
        ElMessage.error(response.message || '上传失败')
      }
    }

    const handleReupload = () => {
      form.value.certificateFile = ''
    }

    const handleSave = async () => {
      if (!form.value.certificateNo || !form.value.productName) {
        ElMessage.warning('请填写证书编号和产品名称')
        return
      }
      try {
        const res = await api.post('/admin/certificate/save', form.value)
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
        const res = await api.delete(`/admin/certificate/${id}`)
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

    // 判断是否为图片文件
    const isImageFile = (filePath) => {
      if (!filePath) return false
      const imageExtensions = ['.jpg', '.jpeg', '.png', '.gif', '.bmp', '.webp', '.svg']
      const lowerPath = filePath.toLowerCase()
      return imageExtensions.some(ext => lowerPath.endsWith(ext))
    }

    // 判断是否为PDF文件
    const isPdfFile = (filePath) => {
      if (!filePath) return false
      return filePath.toLowerCase().endsWith('.pdf')
    }

    // 获取文件名
    const getFileName = (filePath) => {
      if (!filePath) return ''
      const parts = filePath.split('/')
      return parts[parts.length - 1]
    }

    // 处理图片加载错误
    const handleImageError = (event) => {
      if (event && event.target) {
        event.target.style.display = 'none'
      }
    }

    // 查看文件
    const viewFile = (filePath) => {
      if (isPdfFile(filePath)) {
        // PDF文件直接使用完整URL
        viewFileUrl.value = getImageUrl(filePath)
      } else {
        // 图片文件使用getImageUrl处理
        viewFileUrl.value = getImageUrl(filePath)
      }
      fileDialogVisible.value = true
    }

    // 下载文件
    const downloadFile = (fileUrl) => {
      const link = document.createElement('a')
      link.href = fileUrl
      link.download = getFileName(fileUrl)
      link.target = '_blank'
      document.body.appendChild(link)
      link.click()
      document.body.removeChild(link)
    }

    onMounted(() => {
      loadData()
    })

    return {
      tableData,
      currentPage,
      pageSize,
      total,
      keyword,
      dialogVisible,
      dialogTitle,
      fileDialogVisible,
      viewFileUrl,
      form,
      uploadUrl,
      uploadHeaders,
      loadData,
      handleAdd,
      handleEdit,
      handleFileSuccess,
      handleReupload,
      handleSave,
      handleDelete,
      viewFile,
      getImageUrl,
      isImageFile,
      isPdfFile,
      getFileName,
      handleImageError,
      downloadFile,
      Document,
      Download
    }
  }
}
</script>

