<template>
  <el-card>
    <template #header>
      <span>公司信息管理</span>
    </template>
    <el-form
      ref="formRef"
      :model="form"
      label-width="120px"
      :loading="loading"
    >
      <el-form-item label="中文名称">
        <el-input v-model="form.nameCn" />
      </el-form-item>
      <el-form-item label="英文名称">
        <el-input v-model="form.nameEn" />
      </el-form-item>
      <el-form-item label="公司简介（中文）">
        <el-input v-model="form.descriptionCn" type="textarea" :rows="6" placeholder="请输入公司简介（中文）" />
      </el-form-item>
      <el-form-item label="公司简介（英文）">
        <el-input v-model="form.descriptionEn" type="textarea" :rows="6" placeholder="请输入公司简介（英文）" />
      </el-form-item>
      <el-form-item label="地址">
        <el-input v-model="form.address" />
      </el-form-item>
      <el-form-item label="电话">
        <el-input v-model="form.phone" />
      </el-form-item>
      <el-form-item label="手机号">
        <el-input v-model="form.mobile" />
      </el-form-item>
      <el-form-item label="邮箱">
        <el-input v-model="form.email" />
      </el-form-item>
      <el-form-item label="QQ">
        <el-input v-model="form.qq" />
      </el-form-item>
      <el-form-item label="微信">
        <el-input v-model="form.wechat" />
      </el-form-item>
      <el-form-item label="Logo">
        <el-upload
          :action="uploadUrl"
          :headers="uploadHeaders"
          :on-success="handleLogoSuccess"
          :show-file-list="false"
          drag
          accept="image/*"
        >
          <el-icon v-if="!form.logo" class="el-icon--upload"><upload-filled /></el-icon>
          <div v-if="!form.logo" class="el-upload__text">
            将Logo拖到此处，或<em>点击上传</em>
          </div>
          <div v-if="form.logo" style="text-align: center;">
            <img :src="getImageUrl(form.logo)" style="max-width: 200px; max-height: 200px; border-radius: 4px;" />
            <div style="margin-top: 10px;">
              <el-button size="small" type="primary" @click.stop="handleReuploadLogo">重新上传</el-button>
              <el-button size="small" type="danger" @click.stop="form.logo = ''">删除</el-button>
            </div>
          </div>
          <template #tip>
            <div class="el-upload__tip" v-if="!form.logo">
              支持拖拽上传，只能上传图片文件
            </div>
          </template>
        </el-upload>
      </el-form-item>
      <el-form-item label="官方账号二维码">
        <el-upload
          :action="uploadUrl"
          :headers="uploadHeaders"
          :on-success="handleQRCodeSuccess"
          :show-file-list="false"
          drag
          accept="image/*"
        >
          <el-icon v-if="!form.qrCode" class="el-icon--upload"><upload-filled /></el-icon>
          <div v-if="!form.qrCode" class="el-upload__text">
            将二维码拖到此处，或<em>点击上传</em>
          </div>
          <div v-if="form.qrCode" style="text-align: center;">
            <img :src="getImageUrl(form.qrCode)" style="max-width: 200px; max-height: 200px; border-radius: 4px;" />
            <div style="margin-top: 10px;">
              <el-button size="small" type="primary" @click.stop="handleReuploadQRCode">重新上传</el-button>
              <el-button size="small" type="danger" @click.stop="form.qrCode = ''">删除</el-button>
            </div>
          </div>
          <template #tip>
            <div class="el-upload__tip" v-if="!form.qrCode">
              支持拖拽上传，只能上传图片文件
            </div>
          </template>
        </el-upload>
        <div style="margin-top: 5px; color: #909399; font-size: 12px;">
          提示：二维码将显示在底部菜单的联系方式右侧
        </div>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleSave" :loading="saving">保存</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { UploadFilled } from '@element-plus/icons-vue'
import { getCompanyInfo, updateCompanyInfo } from '../../api/admin'
import { getImageUrl } from '../../utils/image'

export default {
  name: 'AdminCompany',
  components: {
    UploadFilled
  },
  setup() {
    const formRef = ref(null)
    const loading = ref(false)
    const saving = ref(false)
    const form = reactive({
      nameCn: '',
      nameEn: '',
      descriptionCn: '',
      descriptionEn: '',
      address: '',
      phone: '',
      mobile: '',
      email: '',
      qq: '',
      wechat: '',
      logo: '',
      qrCode: ''
    })

    const uploadUrl = `${import.meta.env.VITE_API_BASE_URL || (import.meta.env.DEV ? 'http://localhost:8082/api' : '/api')}/file/upload`
    const uploadHeaders = {
      Authorization: `Bearer ${localStorage.getItem('admin_token')}`
    }

    onMounted(async () => {
      loading.value = true
      try {
        const res = await getCompanyInfo()
        if (res.code === 200 && res.data) {
          Object.assign(form, res.data)
        }
      } catch (error) {
        ElMessage.error('加载数据失败')
      } finally {
        loading.value = false
      }
    })

    const handleLogoSuccess = (response) => {
      if (response.code === 200) {
        form.logo = response.data
        ElMessage.success('上传成功')
      }
    }

    const handleQRCodeSuccess = (response) => {
      if (response.code === 200) {
        form.qrCode = response.data
        ElMessage.success('上传成功')
      }
    }

    const handleReuploadLogo = () => {
      form.logo = ''
    }

    const handleReuploadQRCode = () => {
      form.qrCode = ''
    }

    const handleSave = async () => {
      saving.value = true
      try {
        const res = await updateCompanyInfo(form)
        if (res.code === 200) {
          ElMessage.success('保存成功')
        } else {
          ElMessage.error(res.message || '保存失败')
        }
      } catch (error) {
        ElMessage.error('保存失败')
      } finally {
        saving.value = false
      }
    }

    return {
      formRef,
      form,
      loading,
      saving,
      uploadUrl,
      uploadHeaders,
      handleLogoSuccess,
      handleQRCodeSuccess,
      handleReuploadLogo,
      handleReuploadQRCode,
      handleSave,
      getImageUrl
    }
  }
}
</script>

