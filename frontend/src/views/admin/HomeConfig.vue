<template>
  <el-card>
    <template #header>
      <span>首页配置管理</span>
    </template>
    
    <el-tabs v-model="activeTab">
      <!-- Banner配置 -->
      <el-tab-pane label="Banner配置" name="banner">
        <el-form :model="bannerForm" label-width="150px" style="max-width: 800px;">
          <el-form-item label="主标题（中文）">
            <el-input v-model="bannerForm.banner_title_cn" placeholder="请输入Banner主标题（中文）" />
          </el-form-item>
          <el-form-item label="主标题（英文）">
            <el-input v-model="bannerForm.banner_title_en" placeholder="请输入Banner主标题（英文）" />
          </el-form-item>
          <el-form-item label="副标题（中文）">
            <el-input v-model="bannerForm.banner_subtitle_cn" placeholder="请输入Banner副标题（中文）" />
          </el-form-item>
          <el-form-item label="副标题（英文）">
            <el-input v-model="bannerForm.banner_subtitle_en" placeholder="请输入Banner副标题（英文）" />
          </el-form-item>
          <el-form-item label="描述（中文）">
            <el-input v-model="bannerForm.banner_desc_cn" placeholder="请输入Banner描述（中文）" />
          </el-form-item>
          <el-form-item label="描述（英文）">
            <el-input v-model="bannerForm.banner_desc_en" placeholder="请输入Banner描述（英文）" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="saveBannerConfig" :loading="saving">保存Banner配置</el-button>
          </el-form-item>
        </el-form>
      </el-tab-pane>
      
      <!-- Banner价值观 -->
      <el-tab-pane label="Banner价值观" name="values">
        <div style="margin-bottom: 20px;">
          <el-button type="primary" @click="handleAddValue">新增价值观</el-button>
        </div>
        <el-table :data="bannerValues" border>
          <el-table-column prop="title" label="标题" width="200" />
          <el-table-column prop="description" label="描述" show-overflow-tooltip />
          <el-table-column prop="sortOrder" label="排序" width="100" />
          <el-table-column label="操作" width="200">
            <template #default="{ row }">
              <el-button size="small" @click="handleEditValue(row)">编辑</el-button>
              <el-button size="small" type="danger" @click="handleDeleteValue(row.id)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        
        <el-dialog v-model="valueDialogVisible" :title="valueDialogTitle" width="600px">
          <el-form :model="valueForm" label-width="100px">
            <el-form-item label="标题">
              <el-input v-model="valueForm.title" />
            </el-form-item>
            <el-form-item label="描述">
              <el-input v-model="valueForm.description" type="textarea" :rows="4" />
            </el-form-item>
            <el-form-item label="排序">
              <el-input-number v-model="valueForm.sortOrder" :min="0" />
            </el-form-item>
          </el-form>
          <template #footer>
            <el-button @click="valueDialogVisible = false">取消</el-button>
            <el-button type="primary" @click="handleSaveValue">保存</el-button>
          </template>
        </el-dialog>
      </el-tab-pane>
      
      <!-- 介绍文本配置 -->
      <el-tab-pane label="介绍文本配置" name="intro">
        <el-form :model="introForm" label-width="150px" style="max-width: 800px;">
          <el-form-item label="企业优势介绍（中文）">
            <el-input v-model="introForm.advantage_intro_cn" type="textarea" :rows="4" placeholder="请输入企业优势介绍文本（中文）" />
          </el-form-item>
          <el-form-item label="企业优势介绍（英文）">
            <el-input v-model="introForm.advantage_intro_en" type="textarea" :rows="4" placeholder="请输入企业优势介绍文本（英文）" />
          </el-form-item>
          <el-form-item label="荣誉资质介绍（中文）">
            <el-input v-model="introForm.honor_intro_cn" type="textarea" :rows="4" placeholder="请输入荣誉资质介绍文本（中文）" />
          </el-form-item>
          <el-form-item label="荣誉资质介绍（英文）">
            <el-input v-model="introForm.honor_intro_en" type="textarea" :rows="4" placeholder="请输入荣誉资质介绍文本（英文）" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="saveIntroConfig" :loading="saving">保存介绍文本</el-button>
          </el-form-item>
        </el-form>
      </el-tab-pane>
    </el-tabs>
  </el-card>
</template>

<script>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import axios from 'axios'
import { getHomeConfigs } from '../../api/homeConfig'

export default {
  name: 'AdminHomeConfig',
  setup() {
    const activeTab = ref('banner')
    const saving = ref(false)
    const bannerValues = ref([])
    const valueDialogVisible = ref(false)
    const valueDialogTitle = ref('新增价值观')
    const valueForm = ref({
      id: null,
      title: '',
      description: '',
      sortOrder: 0
    })
    
    const bannerForm = reactive({
      banner_title_cn: '',
      banner_title_en: '',
      banner_subtitle_cn: '',
      banner_subtitle_en: '',
      banner_desc_cn: '',
      banner_desc_en: ''
    })
    
    const introForm = reactive({
      advantage_intro_cn: '',
      advantage_intro_en: '',
      honor_intro_cn: '',
      honor_intro_en: ''
    })
    
    const api = axios.create({
      baseURL: import.meta.env.VITE_API_BASE_URL || (import.meta.env.DEV ? 'http://localhost:8082/api' : '/api'),
      headers: {
        Authorization: `Bearer ${localStorage.getItem('admin_token')}`
      }
    })
    
    const loadConfigs = async () => {
      try {
        const res = await getHomeConfigs()
        if (res.code === 200) {
          const configs = res.data || []
          // 填充Banner表单
          configs.forEach(config => {
            if (config.configKey === 'banner_title') {
              bannerForm.banner_title_cn = config.configValueCn || ''
              bannerForm.banner_title_en = config.configValueEn || ''
            } else if (config.configKey === 'banner_subtitle') {
              bannerForm.banner_subtitle_cn = config.configValueCn || ''
              bannerForm.banner_subtitle_en = config.configValueEn || ''
            } else if (config.configKey === 'banner_desc') {
              bannerForm.banner_desc_cn = config.configValueCn || ''
              bannerForm.banner_desc_en = config.configValueEn || ''
            } else if (config.configKey === 'advantage_intro') {
              introForm.advantage_intro_cn = config.configValueCn || ''
              introForm.advantage_intro_en = config.configValueEn || ''
            } else if (config.configKey === 'honor_intro') {
              introForm.honor_intro_cn = config.configValueCn || ''
              introForm.honor_intro_en = config.configValueEn || ''
            }
          })
        }
      } catch (error) {
        console.error('加载配置失败:', error)
      }
    }
    
    const loadBannerValues = async () => {
      try {
        const res = await api.get('/admin/banner-value/list')
        if (res.data.code === 200) {
          bannerValues.value = res.data.data || []
        }
      } catch (error) {
        ElMessage.error('加载价值观数据失败')
      }
    }
    
    const saveBannerConfig = async () => {
      saving.value = true
      try {
        const configs = [
          { configKey: 'banner_title', configValueCn: bannerForm.banner_title_cn, configValueEn: bannerForm.banner_title_en, configType: 'text', sortOrder: 1 },
          { configKey: 'banner_subtitle', configValueCn: bannerForm.banner_subtitle_cn, configValueEn: bannerForm.banner_subtitle_en, configType: 'text', sortOrder: 2 },
          { configKey: 'banner_desc', configValueCn: bannerForm.banner_desc_cn, configValueEn: bannerForm.banner_desc_en, configType: 'text', sortOrder: 3 }
        ]
        const res = await api.post('/admin/home-config/batch-save', configs)
        if (res.data.code === 200) {
          ElMessage.success('保存成功')
        } else {
          ElMessage.error(res.data.message || '保存失败')
        }
      } catch (error) {
        ElMessage.error('保存失败')
      } finally {
        saving.value = false
      }
    }
    
    const saveIntroConfig = async () => {
      saving.value = true
      try {
        const configs = [
          { configKey: 'advantage_intro', configValueCn: introForm.advantage_intro_cn, configValueEn: introForm.advantage_intro_en, configType: 'text', sortOrder: 4 },
          { configKey: 'honor_intro', configValueCn: introForm.honor_intro_cn, configValueEn: introForm.honor_intro_en, configType: 'text', sortOrder: 5 }
        ]
        const res = await api.post('/admin/home-config/batch-save', configs)
        if (res.data.code === 200) {
          ElMessage.success('保存成功')
        } else {
          ElMessage.error(res.data.message || '保存失败')
        }
      } catch (error) {
        ElMessage.error('保存失败')
      } finally {
        saving.value = false
      }
    }
    
    const handleAddValue = () => {
      valueDialogTitle.value = '新增价值观'
      valueForm.value = {
        id: null,
        title: '',
        description: '',
        sortOrder: bannerValues.value.length + 1
      }
      valueDialogVisible.value = true
    }
    
    const handleEditValue = (row) => {
      valueDialogTitle.value = '编辑价值观'
      valueForm.value = { ...row }
      valueDialogVisible.value = true
    }
    
    const handleSaveValue = async () => {
      try {
        const res = await api.post('/admin/banner-value/save', valueForm.value)
        if (res.data.code === 200) {
          ElMessage.success('保存成功')
          valueDialogVisible.value = false
          loadBannerValues()
        } else {
          ElMessage.error(res.data.message || '保存失败')
        }
      } catch (error) {
        ElMessage.error('保存失败')
      }
    }
    
    const handleDeleteValue = async (id) => {
      try {
        await ElMessageBox.confirm('确定要删除吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
        const res = await api.delete(`/admin/banner-value/${id}`)
        if (res.data.code === 200) {
          ElMessage.success('删除成功')
          loadBannerValues()
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
      loadConfigs()
      loadBannerValues()
    })
    
    return {
      activeTab,
      saving,
      bannerForm,
      introForm,
      bannerValues,
      valueDialogVisible,
      valueDialogTitle,
      valueForm,
      saveBannerConfig,
      saveIntroConfig,
      handleAddValue,
      handleEditValue,
      handleSaveValue,
      handleDeleteValue
    }
  }
}
</script>

