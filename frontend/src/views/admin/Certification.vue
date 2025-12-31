<template>
  <el-card>
    <template #header>
      <div style="display: flex; justify-content: space-between; align-items: center;">
        <span>认证服务管理</span>
        <div>
          <el-popover
            placement="bottom-start"
            :width="300"
            trigger="click"
            v-model:visible="filterCategoryVisible"
          >
            <template #reference>
              <el-input
                v-model="filterCategoryName"
                placeholder="筛选分类"
                readonly
                clearable
                style="width: 200px; margin-right: 10px;"
                @clear="handleClearFilter"
              >
                <template #prefix>
                  <el-icon><Search /></el-icon>
                </template>
                <template #suffix>
                  <el-icon v-if="filterCategoryName" class="el-input__clear" @click="handleClearFilter" style="cursor: pointer;">
                    <Close />
                  </el-icon>
                </template>
              </el-input>
            </template>
            <el-tree
              :data="categoryTreeData"
              :props="{ children: 'children', label: 'nameCn' }"
              node-key="id"
              :default-expand-all="false"
              :expand-on-click-node="false"
              @node-click="handleCategoryNodeClick"
              :highlight-current="true"
            >
              <template #default="{ node, data }">
                <span class="tree-node-text">
                  {{ data.nameCn }}
                  <span v-if="data.nameEn" style="color: #909399; font-size: 12px; margin-left: 5px;">
                    ({{ data.nameEn }})
                  </span>
                </span>
              </template>
            </el-tree>
          </el-popover>
          <el-button type="danger" :disabled="selectedIds.length === 0" @click="handleBatchDelete" style="margin-right: 10px;">
            批量删除 ({{ selectedIds.length }})
          </el-button>
          <el-button type="success" @click="showImportDialog = true" style="margin-right: 10px;">
            <el-icon style="margin-right: 5px;"><Upload /></el-icon>
            批量导入
          </el-button>
          <el-button type="primary" @click="handleAdd">新增认证服务</el-button>
        </div>
      </div>
    </template>
    <el-table :data="tableData" border @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" />
      <el-table-column prop="nameCn" label="中文名称" width="150" />
      <el-table-column prop="nameEn" label="英文名称" width="150" />
      <el-table-column label="封面图片" width="120">
        <template #default="{ row }">
          <div v-if="row.image" style="display: flex; align-items: center; justify-content: center;">
            <img :src="getImageUrl(row.image)" style="max-width: 80px; max-height: 60px; border-radius: 4px; border: 1px solid #dcdfe6; object-fit: cover;" />
          </div>
          <span v-else style="color: #909399;">-</span>
        </template>
      </el-table-column>
      <el-table-column label="分类" width="120">
        <template #default="{ row }">
          <span>{{ getCategoryName(row.categoryId) || '-' }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="region" label="地区" width="100" />
      <el-table-column prop="descriptionCn" label="中文描述" width="300">
        <template #default="{ row }">
          <div class="description-cell">
            {{ row.descriptionCn || '-' }}
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="sortOrder" label="排序" width="80" />
      <el-table-column label="操作" width="200" fixed="right">
        <template #default="{ row }">
          <el-button size="small" @click="handleEdit(row)">编辑</el-button>
          <el-button size="small" type="danger" @click="handleDelete(row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="800px">
      <el-form :model="form" label-width="120px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="中文名称">
              <el-input v-model="form.nameCn" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="英文名称">
              <el-input v-model="form.nameEn" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="分类">
              <el-tree-select
                v-model="form.categoryId"
                :data="categoryTreeData"
                :props="{ children: 'children', label: 'nameCn', value: 'id' }"
                placeholder="选择分类"
                clearable
                style="width: 100%;"
                @change="handleCategoryChange"
                :default-expand-all="false"
                :check-strictly="true"
                :render-after-expand="false"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="父服务">
              <el-select v-model="form.parentId" placeholder="选择父服务（留空为顶级服务）" clearable style="width: 100%;">
                <el-option label="顶级服务" :value="0" />
                <el-option 
                  v-for="service in topServices" 
                  :key="service.id" 
                  :label="service.nameCn" 
                  :value="service.id"
                  :disabled="service.id === form.id"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="地区">
              <el-input v-model="form.region" placeholder="如：欧盟、美国、中国等" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="中文简介">
              <el-input v-model="form.summaryCn" type="textarea" :rows="2" placeholder="请输入中文简介（显示在封面图片下方）" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="英文简介">
              <el-input v-model="form.summaryEn" type="textarea" :rows="2" placeholder="请输入英文简介（显示在封面图片下方）" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="封面图片">
          <el-upload
            ref="coverImageUpload"
            :action="uploadUrl"
            :headers="uploadHeaders"
            :on-success="(response) => handleImageSuccess(response, 'image')"
            :show-file-list="false"
            drag
            accept="image/*"
            class="cover-image-upload"
          >
            <el-icon v-if="!form.image" class="el-icon--upload"><upload-filled /></el-icon>
            <div v-if="!form.image" class="el-upload__text">
              将文件拖到此处，或<em>点击上传</em>
            </div>
            <div v-if="form.image">
              <img :src="getImageUrl(form.image)" class="uploaded-image-preview" />
              <div class="image-actions">
                <el-button size="small" type="primary" @click.stop="handleReupload">
                  重新上传
                </el-button>
                <el-button size="small" type="danger" @click.stop="form.image = ''">删除图片</el-button>
              </div>
            </div>
            <template #tip>
              <div class="el-upload__tip" v-if="!form.image">
                支持拖拽上传，只能上传图片文件
              </div>
            </template>
          </el-upload>
          <div style="margin-top: 5px; color: #909399; font-size: 12px;">
            提示：封面图片用于列表展示，与描述内容类型无关
          </div>
        </el-form-item>
        <el-form-item label="中文描述">
          <div style="width: 100%;">
            <el-select v-model="form.contentTypeCn" placeholder="选择内容类型" style="width: 200px; margin-bottom: 10px;">
              <el-option label="文字" value="text" />
              <el-option label="图片" value="image" />
              <el-option label="HTML代码" value="html" />
            </el-select>
            <!-- 文字类型 -->
            <el-input 
              v-if="form.contentTypeCn === 'text' || !form.contentTypeCn" 
              v-model="form.descriptionCn" 
              type="textarea" 
              :rows="4" 
              placeholder="请输入文字描述"
            />
            <!-- 图片类型 -->
            <div v-if="form.contentTypeCn === 'image'">
              <el-upload
                :action="uploadUrl"
                :headers="uploadHeaders"
                :on-success="(response) => handleImageSuccess(response, 'descriptionCn')"
                :show-file-list="false"
                drag
                accept="image/*"
              >
                <el-icon class="el-icon--upload"><upload-filled /></el-icon>
                <div class="el-upload__text">
                  将图片拖到此处，或<em>点击上传</em>
                </div>
                <template #tip>
                  <div class="el-upload__tip">
                    支持拖拽上传，只能上传图片文件
                  </div>
                </template>
              </el-upload>
              <div v-if="form.descriptionCn" style="margin-top: 10px;">
                <img :src="getImageUrl(form.descriptionCn)" style="max-width: 400px; max-height: 300px; display: block; margin-top: 10px; border-radius: 4px;" />
                <el-button size="small" type="danger" style="margin-top: 10px;" @click="form.descriptionCn = ''">删除图片</el-button>
              </div>
            </div>
            <!-- HTML代码类型 -->
            <div v-if="form.contentTypeCn === 'html'">
              <el-input 
                v-model="form.descriptionCn" 
                type="textarea" 
                :rows="8" 
                placeholder="请输入HTML代码"
              />
              <el-button size="small" style="margin-top: 10px;" @click="showPreviewCn = !showPreviewCn">
                {{ showPreviewCn ? '隐藏预览' : '显示预览' }}
              </el-button>
              <div v-if="showPreviewCn && form.descriptionCn" style="margin-top: 10px; padding: 10px; border: 1px solid #dcdfe6; border-radius: 4px; background: #fff;">
                <div v-html="form.descriptionCn"></div>
              </div>
            </div>
          </div>
        </el-form-item>
        <el-form-item label="英文描述">
          <div style="width: 100%;">
            <el-select v-model="form.contentTypeEn" placeholder="选择内容类型" style="width: 200px; margin-bottom: 10px;">
              <el-option label="文字" value="text" />
              <el-option label="图片" value="image" />
              <el-option label="HTML代码" value="html" />
            </el-select>
            <!-- 文字类型 -->
            <el-input 
              v-if="form.contentTypeEn === 'text' || !form.contentTypeEn" 
              v-model="form.descriptionEn" 
              type="textarea" 
              :rows="4" 
              placeholder="请输入文字描述"
            />
            <!-- 图片类型 -->
            <div v-if="form.contentTypeEn === 'image'">
              <el-upload
                :action="uploadUrl"
                :headers="uploadHeaders"
                :on-success="(response) => handleImageSuccess(response, 'descriptionEn')"
                :show-file-list="false"
                drag
                accept="image/*"
              >
                <el-icon class="el-icon--upload"><upload-filled /></el-icon>
                <div class="el-upload__text">
                  将图片拖到此处，或<em>点击上传</em>
                </div>
                <template #tip>
                  <div class="el-upload__tip">
                    支持拖拽上传，只能上传图片文件
                  </div>
                </template>
              </el-upload>
              <div v-if="form.descriptionEn" style="margin-top: 10px;">
                <img :src="getImageUrl(form.descriptionEn)" style="max-width: 400px; max-height: 300px; display: block; margin-top: 10px; border-radius: 4px;" />
                <el-button size="small" type="danger" style="margin-top: 10px;" @click="form.descriptionEn = ''">删除图片</el-button>
              </div>
            </div>
            <!-- HTML代码类型 -->
            <div v-if="form.contentTypeEn === 'html'">
              <el-input 
                v-model="form.descriptionEn" 
                type="textarea" 
                :rows="8" 
                placeholder="请输入HTML代码"
              />
              <el-button size="small" style="margin-top: 10px;" @click="showPreviewEn = !showPreviewEn">
                {{ showPreviewEn ? '隐藏预览' : '显示预览' }}
              </el-button>
              <div v-if="showPreviewEn && form.descriptionEn" style="margin-top: 10px; padding: 10px; border: 1px solid #dcdfe6; border-radius: 4px; background: #fff;">
                <div v-html="form.descriptionEn"></div>
              </div>
            </div>
          </div>
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

    <!-- 批量导入对话框 -->
    <el-dialog v-model="showImportDialog" title="批量导入认证服务" width="600px">
      <div style="margin-bottom: 20px; text-align: right;">
        <el-button type="primary" @click="downloadTemplate" size="small">
          <el-icon style="margin-right: 5px;"><Download /></el-icon>
          下载模板
        </el-button>
      </div>
      <el-alert
        title="导入说明"
        type="info"
        :closable="false"
        style="margin-bottom: 20px;"
      >
        <template #default>
          <div style="line-height: 1.8;">
            <p>1. Excel 文件第一列应为分类名称（如：欧洲认证、美洲认证等）</p>
            <p>2. 支持的列名：中文名称、英文名称、地区、封面图片、中文简介、英文简介、中文描述、英文描述、排序</p>
            <p>3. 如果封面图片或描述是图片URL，系统会自动下载</p>
            <p>4. 分类名称必须与系统分类匹配</p>
          </div>
        </template>
      </el-alert>
      <el-upload
        :action="importUrl"
        :headers="uploadHeaders"
        :on-success="handleImportSuccess"
        :on-error="handleImportError"
        :before-upload="beforeUpload"
        :show-file-list="false"
        accept=".xlsx,.xls"
        drag
        class="excel-upload"
      >
        <el-icon class="el-icon--upload"><upload-filled /></el-icon>
        <div class="el-upload__text">
          将 Excel 文件拖到此处，或<em>点击上传</em>
        </div>
        <template #tip>
          <div class="el-upload__tip">
            只能上传 Excel 文件（.xlsx, .xls），且不超过 10MB
          </div>
        </template>
      </el-upload>
      <div v-if="importResult" style="margin-top: 20px;">
        <el-alert
          :title="importResult.success ? '导入成功' : '导入失败'"
          :type="importResult.success ? 'success' : 'error'"
          :closable="false"
        >
          <template #default>
            <div v-if="importResult.success">
              <p>成功导入: {{ importResult.successCount }} 条</p>
              <p v-if="importResult.failCount > 0">失败: {{ importResult.failCount }} 条</p>
              <p v-if="importResult.message">{{ importResult.message }}</p>
            </div>
            <div v-else>
              <p>{{ importResult.message }}</p>
            </div>
          </template>
        </el-alert>
      </div>
      <template #footer>
        <el-button @click="showImportDialog = false">关闭</el-button>
      </template>
    </el-dialog>
  </el-card>
</template>

<script>
import { ref, reactive, onMounted, computed, watch } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { UploadFilled, Search, Close, Upload, Download } from '@element-plus/icons-vue'
import axios from 'axios'
import { getImageUrl } from '../../utils/image'

export default {
  name: 'AdminCertification',
  components: {
    UploadFilled,
    Search,
    Close,
    Upload,
    Download
  },
  setup() {
    const tableData = ref([])
    const categories = ref([])
    const categoryTreeData = ref([])
    const topServices = ref([])
    const dialogVisible = ref(false)
    const dialogTitle = ref('新增认证服务')
    const filterCategoryId = ref(null)
    const filterCategoryName = ref('')
    const filterCategoryVisible = ref(false)
    const showImportDialog = ref(false)
    const importResult = ref(null)
    const importUrl = `${import.meta.env.VITE_API_BASE_URL || (import.meta.env.DEV ? 'http://localhost:8082/api' : '/api')}/admin/certification/import`
    const showPreviewCn = ref(false)
    const showPreviewEn = ref(false)
    const selectedIds = ref([])
    const form = reactive({
      id: null,
      nameCn: '',
      nameEn: '',
      category: '',
      categoryId: null,
      parentId: 0,
      region: '',
      summaryCn: '', // 中文简介
      summaryEn: '', // 英文简介
      image: '', // 封面图片
      descriptionCn: '',
      descriptionEn: '',
      contentTypeCn: 'text', // 内容类型：text, image, html
      contentTypeEn: 'text', // 内容类型：text, image, html
      sortOrder: 0
    })

    const uploadUrl = `${import.meta.env.VITE_API_BASE_URL || (import.meta.env.DEV ? 'http://localhost:8082/api' : '/api')}/file/upload`
    const uploadHeaders = {
      Authorization: `Bearer ${localStorage.getItem('admin_token')}`
    }

    const api = axios.create({
      baseURL: import.meta.env.VITE_API_BASE_URL || (import.meta.env.DEV ? 'http://localhost:8082/api' : '/api'),
      headers: {
        Authorization: `Bearer ${localStorage.getItem('admin_token')}`
      }
    })

    // 将扁平数据转换为树形结构
    const buildCategoryTree = (data) => {
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

    const loadCategories = async () => {
      try {
        const res = await api.get('/admin/certification-category/list')
        if (res.data.code === 200) {
          categories.value = res.data.data
          // 转换为树形结构
          categoryTreeData.value = buildCategoryTree(res.data.data)
        }
      } catch (error) {
        console.error('加载分类失败:', error)
      }
    }

    const handleCategoryNodeClick = (data) => {
      filterCategoryId.value = data.id
      filterCategoryName.value = data.nameCn
      filterCategoryVisible.value = false
      loadData()
    }

    const handleClearFilter = () => {
      filterCategoryId.value = null
      filterCategoryName.value = ''
      loadData()
    }

    const beforeUpload = (file) => {
      const isExcel = file.type === 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' || 
                     file.type === 'application/vnd.ms-excel'
      const isLt10M = file.size / 1024 / 1024 < 10

      if (!isExcel) {
        ElMessage.error('只能上传 Excel 文件!')
        return false
      }
      if (!isLt10M) {
        ElMessage.error('文件大小不能超过 10MB!')
        return false
      }
      importResult.value = null
      return true
    }

    const handleImportSuccess = (response) => {
      if (response.code === 200) {
        importResult.value = {
          success: true,
          successCount: response.data.successCount || 0,
          failCount: response.data.failCount || 0,
          message: response.data.message || '导入完成'
        }
        ElMessage.success(`导入成功！成功: ${response.data.successCount || 0} 条，失败: ${response.data.failCount || 0} 条`)
        // 刷新数据
        setTimeout(() => {
          loadData()
        }, 1000)
      } else {
        importResult.value = {
          success: false,
          message: response.message || '导入失败'
        }
        ElMessage.error(response.message || '导入失败')
      }
    }

    const handleImportError = (error) => {
      importResult.value = {
        success: false,
        message: '文件上传失败，请重试'
      }
      ElMessage.error('文件上传失败')
    }

    const downloadTemplate = () => {
      const templateUrl = `${import.meta.env.VITE_API_BASE_URL || (import.meta.env.DEV ? 'http://localhost:8082/api' : '/api')}/admin/certification/template`
      const link = document.createElement('a')
      link.href = templateUrl
      link.download = '认证服务导入模板.xlsx'
      link.style.display = 'none'
      document.body.appendChild(link)
      link.click()
      document.body.removeChild(link)
      ElMessage.success('模板下载中...')
    }

    const loadTopServices = async () => {
      try {
        const res = await api.get('/admin/certification/list')
        if (res.data.code === 200) {
          // 获取所有parentId为0或null的服务
          topServices.value = res.data.data.filter(s => !s.parentId || s.parentId === 0)
        }
      } catch (error) {
        console.error('加载顶级服务失败:', error)
      }
    }

    const loadData = async () => {
      try {
        let url = '/admin/certification/list'
        const res = await api.get(url)
        if (res.data.code === 200) {
          let data = res.data.data
          // 如果选择了分类，进行筛选
          if (filterCategoryId.value) {
            data = data.filter(item => item.categoryId === filterCategoryId.value)
          }
          tableData.value = data
        }
      } catch (error) {
        ElMessage.error('加载数据失败')
      }
    }

    const handleCategoryChange = (value) => {
      console.log('handleCategoryChange 被调用，原始值:', value, '类型:', typeof value, '是否为数组:', Array.isArray(value))
      
      // 确保 categoryId 是数字类型
      if (value !== null && value !== undefined) {
        let categoryId = value
        
        // 如果是数组，取第一个值
        if (Array.isArray(categoryId)) {
          console.log('值是数组，长度:', categoryId.length, '内容:', categoryId)
          categoryId = categoryId.length > 0 ? categoryId[0] : null
        }
        
        // 如果是对象，取 id 属性
        if (categoryId && typeof categoryId === 'object') {
          console.log('值是对象，对象内容:', categoryId)
          if (categoryId.id !== undefined) {
            categoryId = categoryId.id
          } else {
            console.warn('对象中没有 id 属性，对象:', categoryId)
          }
        }
        
        // 转换为数字
        if (categoryId !== null && categoryId !== undefined) {
          const numCategoryId = Number(categoryId)
          form.categoryId = numCategoryId
          console.log('分类选择变化 - 原始值:', value, '提取的 categoryId:', categoryId, '转换后的数字 categoryId:', numCategoryId)
          
          // 验证分类是否存在
          const foundCategory = categories.value.find(cat => cat.id === numCategoryId)
          if (foundCategory) {
            console.log('找到对应分类:', foundCategory.nameCn, '(ID:', foundCategory.id, ')')
          } else {
            console.warn('警告：未找到 ID 为', numCategoryId, '的分类！所有分类ID:', categories.value.map(c => c.id))
          }
        } else {
          form.categoryId = null
          console.log('categoryId 设置为 null')
        }
      } else {
        form.categoryId = null
        console.log('值为 null 或 undefined，categoryId 设置为 null')
      }
      // 分类改变时，重新加载顶级服务
      loadTopServices()
    }

    // 根据分类ID获取分类名称
    const getCategoryName = (categoryId) => {
      if (!categoryId) return ''
      const category = categories.value.find(cat => cat.id === categoryId)
      return category ? category.nameCn : ''
    }

    // 监听 form.categoryId 的变化，用于调试和自动修正
    watch(() => form.categoryId, (newVal, oldVal) => {
      console.log('form.categoryId 变化:', {
        oldValue: oldVal,
        newValue: newVal,
        newValueType: typeof newVal,
        isArray: Array.isArray(newVal),
        isObject: typeof newVal === 'object' && newVal !== null
      })
      
      // 如果新值是数组或对象，立即修正
      if (newVal !== null && newVal !== undefined) {
        let correctedValue = newVal
        if (Array.isArray(correctedValue)) {
          correctedValue = correctedValue.length > 0 ? correctedValue[0] : null
        }
        if (correctedValue && typeof correctedValue === 'object' && correctedValue.id !== undefined) {
          correctedValue = correctedValue.id
        }
        if (correctedValue !== null && correctedValue !== undefined) {
          const numValue = Number(correctedValue)
          if (numValue !== newVal && !isNaN(numValue)) {
            console.log('自动修正 categoryId:', newVal, '->', numValue)
            // 使用 nextTick 避免无限循环
            setTimeout(() => {
              form.categoryId = numValue
            }, 0)
          }
        }
      }
    }, { immediate: false })

    onMounted(() => {
      loadCategories()
      loadData()
      loadTopServices()
    })

    const handleAdd = () => {
      dialogTitle.value = '新增认证服务'
      showPreviewCn.value = false
      showPreviewEn.value = false
      loadTopServices()
      Object.assign(form, {
        id: null,
        nameCn: '',
        nameEn: '',
        category: '',
        categoryId: null,
        parentId: 0,
        region: '',
        summaryCn: '',
        summaryEn: '',
        image: '',
        descriptionCn: '',
        descriptionEn: '',
        contentTypeCn: 'text',
        contentTypeEn: 'text',
        sortOrder: 0
      })
      dialogVisible.value = true
    }

    const handleEdit = (row) => {
      dialogTitle.value = '编辑认证服务'
      showPreviewCn.value = false
      showPreviewEn.value = false
      loadTopServices()
      // 判断内容类型
      const detectContentType = (content) => {
        if (!content) return 'text'
        // 如果是图片路径（包含常见图片扩展名）
        if (/\.(jpg|jpeg|png|gif|bmp|webp|svg)$/i.test(content)) {
          return 'image'
        }
        // 如果是HTML代码（包含HTML标签）
        if (/<[a-z][\s\S]*>/i.test(content)) {
          return 'html'
        }
        return 'text'
      }
      Object.assign(form, { 
        ...row,
        categoryId: row.categoryId ? Number(row.categoryId) : null,
        parentId: row.parentId ? Number(row.parentId) : 0,
        contentTypeCn: row.contentTypeCn || detectContentType(row.descriptionCn),
        contentTypeEn: row.contentTypeEn || detectContentType(row.descriptionEn)
      })
      dialogVisible.value = true
    }

    const coverImageUpload = ref(null)
    
    const handleReupload = () => {
      if (coverImageUpload.value) {
        const input = coverImageUpload.value.$el.querySelector('input[type="file"]')
        if (input) {
          input.click()
        }
      }
    }

    const handleImageSuccess = (response, field) => {
      console.log('上传响应:', response, '字段:', field)
      if (response.code === 200) {
        form[field] = response.data
        ElMessage.success('上传成功')
        console.log('图片路径已设置:', form[field])
        console.log('完整图片URL:', getImageUrl(form[field]))
      } else {
        ElMessage.error('上传失败: ' + (response.message || '未知错误'))
      }
    }

    const handleSave = async () => {
      // 确保 categoryId 是数字类型
      if (form.categoryId !== null && form.categoryId !== undefined) {
        let categoryId = form.categoryId
        
        // 如果是数组，取第一个值
        if (Array.isArray(categoryId)) {
          categoryId = categoryId.length > 0 ? categoryId[0] : null
        }
        
        // 如果是对象，取 id 属性
        if (categoryId && typeof categoryId === 'object' && categoryId.id !== undefined) {
          categoryId = categoryId.id
        }
        
        // 转换为数字
        if (categoryId !== null && categoryId !== undefined) {
          form.categoryId = Number(categoryId)
        } else {
          form.categoryId = null
        }
      }
      
      // 确保 parentId 是数字类型
      if (form.parentId !== null && form.parentId !== undefined) {
        form.parentId = Number(form.parentId)
      }
      
      console.log('保存服务，提交的数据:', JSON.stringify({
        ...form,
        categoryId: form.categoryId,
        parentId: form.parentId
      }, null, 2))
      
      // 验证必填字段
      if (!form.nameCn) {
        ElMessage.warning('请输入中文名称')
        return
      }
      
      if (!form.categoryId) {
        ElMessage.warning('请选择分类')
        return
      }
      
      try {
        const res = await api.post('/admin/certification/save', form)
        if (res.data.code === 200) {
          ElMessage.success('保存成功')
          dialogVisible.value = false
          loadData()
        } else {
          ElMessage.error('保存失败: ' + (res.data.message || '未知错误'))
        }
      } catch (error) {
        console.error('保存失败:', error)
        ElMessage.error('保存失败: ' + (error.response?.data?.message || error.message || '未知错误'))
      }
    }

    const handleSelectionChange = (selection) => {
      selectedIds.value = selection.map(item => item.id)
    }

    const handleBatchDelete = () => {
      if (selectedIds.value.length === 0) {
        ElMessage.warning('请选择要删除的项目')
        return
      }
      ElMessageBox.confirm(`确定要删除选中的 ${selectedIds.value.length} 项吗？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        try {
          const res = await api.delete('/admin/certification/batch', { data: selectedIds.value })
          if (res.data.code === 200) {
            ElMessage.success('批量删除成功')
            selectedIds.value = []
            loadData()
          }
        } catch (error) {
          ElMessage.error('批量删除失败')
        }
      })
    }

    const handleDelete = (id) => {
      ElMessageBox.confirm('确定要删除吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        try {
          const res = await api.delete(`/admin/certification/${id}`)
          if (res.data.code === 200) {
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
      categories,
      categoryTreeData,
      topServices,
      dialogVisible,
      dialogTitle,
      filterCategoryId,
      filterCategoryName,
      filterCategoryVisible,
      showImportDialog,
      importResult,
      importUrl,
      beforeUpload,
      handleImportSuccess,
      handleImportError,
      downloadTemplate,
      form,
      showPreviewCn,
      showPreviewEn,
      selectedIds,
      uploadUrl,
      uploadHeaders,
      getImageUrl,
      coverImageUpload,
      handleReupload,
      getCategoryName,
      handleAdd,
      handleEdit,
      handleImageSuccess,
      handleSave,
      handleDelete,
      handleSelectionChange,
      handleBatchDelete,
      handleCategoryChange,
      handleCategoryNodeClick,
      handleClearFilter,
      loadData,
      loadCategories,
      loadTopServices
    }
  }
}
</script>

<style scoped>
/* 拖拽上传区域样式优化 */
:deep(.el-upload-dragger) {
  width: 100%;
  height: 180px;
  border: 2px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: all 0.3s;
}

:deep(.el-upload-dragger:hover) {
  border-color: #409eff;
  background-color: #f5f7fa;
}

:deep(.el-icon--upload) {
  font-size: 67px;
  color: #c0c4cc;
  margin: 40px 0 16px;
  line-height: 50px;
}

:deep(.el-upload__text) {
  color: #606266;
  font-size: 14px;
  text-align: center;
}

:deep(.el-upload__text em) {
  color: #409eff;
  font-style: normal;
}

:deep(.el-upload__tip) {
  font-size: 12px;
  color: #909399;
  margin-top: 7px;
  text-align: center;
  position: relative;
  z-index: 1;
}

/* 批量导入对话框中的上传组件样式 */
.excel-upload :deep(.el-upload-dragger) {
  height: 200px;
  padding: 20px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.excel-upload :deep(.el-icon--upload) {
  font-size: 50px;
  color: #c0c4cc;
  margin-bottom: 15px;
  margin-top: 0;
}

.excel-upload :deep(.el-upload__text) {
  color: #606266;
  font-size: 14px;
  text-align: center;
  margin-bottom: 10px;
}

.excel-upload :deep(.el-upload__tip) {
  font-size: 12px;
  color: #909399;
  margin-top: 10px;
  text-align: center;
  position: relative;
  z-index: 1;
  padding-top: 5px;
}

/* 封面图片上传组件样式 */
.cover-image-upload :deep(.el-upload-dragger) {
  height: 200px;
  padding: 20px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  position: relative;
}

.cover-image-upload :deep(.el-icon--upload) {
  font-size: 50px;
  color: #c0c4cc;
  margin-bottom: 15px;
  margin-top: 0;
}

.cover-image-upload :deep(.el-upload__text) {
  color: #606266;
  font-size: 14px;
  text-align: center;
  margin-bottom: 10px;
}

.cover-image-upload :deep(.el-upload__tip) {
  font-size: 12px;
  color: #909399;
  margin-top: 10px;
  text-align: center;
  position: relative;
  z-index: 1;
  padding-top: 5px;
}

.uploaded-image-preview {
  max-width: 100%;
  max-height: 160px;
  border-radius: 4px;
  border: 1px solid #dcdfe6;
  object-fit: contain;
}

.image-actions {
  margin-top: 10px;
  display: flex;
  gap: 10px;
  justify-content: center;
}

.description-cell {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  max-width: 300px;
}

.tree-node-text {
  font-size: 14px;
}

:deep(.el-tree-node__content) {
  height: 32px;
  padding: 5px 0;
}

:deep(.el-tree-node__content:hover) {
  background-color: #f5f7fa;
}

:deep(.el-tree-node__expand-icon) {
  cursor: pointer;
}
</style>

