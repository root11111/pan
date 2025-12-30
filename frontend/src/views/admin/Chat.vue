<template>
  <el-card>
    <template #header>
      <span>实时聊天</span>
    </template>
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card>
          <h3>在线用户</h3>
          <el-scrollbar height="500px">
            <div
              v-for="user in onlineUsers"
              :key="user"
              class="user-item"
              :class="{ active: currentUser === user }"
              @click="selectUser(user)"
            >
              {{ user }}
            </div>
          </el-scrollbar>
        </el-card>
      </el-col>
      <el-col :span="18">
        <el-card>
          <div class="chat-container">
            <div class="chat-messages" ref="messagesRef">
              <div
                v-for="msg in messages"
                :key="msg.id"
                class="message-item"
                :class="{ 'message-right': msg.fromUser === 'admin' }"
              >
                <div class="message-content">{{ msg.content }}</div>
                <div class="message-time">{{ formatTime(msg.createTime) }}</div>
              </div>
            </div>
            <div class="chat-input">
              <el-input
                v-model="inputMessage"
                type="textarea"
                :rows="3"
                placeholder="输入消息..."
                @keyup.ctrl.enter="sendMessage"
              />
              <el-button type="primary" @click="sendMessage" style="margin-top: 10px;">发送</el-button>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </el-card>
</template>

<script>
import { ref, onMounted, onUnmounted, nextTick } from 'vue'
import { ElMessage } from 'element-plus'
import SockJS from 'sockjs-client'
import { Client } from '@stomp/stompjs'

export default {
  name: 'AdminChat',
  setup() {
    const messagesRef = ref(null)
    const messages = ref([])
    const onlineUsers = ref(['user1', 'user2'])
    const currentUser = ref('')
    const inputMessage = ref('')
    const stompClient = ref(null)

    onMounted(() => {
      connectWebSocket()
    })

    onUnmounted(() => {
      if (stompClient.value) {
        stompClient.value.deactivate()
      }
    })

    const connectWebSocket = () => {
      const socket = new SockJS('/ws')
      stompClient.value = new Client({
        webSocketFactory: () => socket,
        reconnectDelay: 5000,
        heartbeatIncoming: 4000,
        heartbeatOutgoing: 4000,
        onConnect: () => {
          ElMessage.success('连接成功')
          stompClient.value.subscribe('/queue/messages/admin', (message) => {
            const msg = JSON.parse(message.body)
            messages.value.push(msg)
            scrollToBottom()
          })
        },
        onStompError: (frame) => {
          ElMessage.error('连接错误: ' + frame.headers['message'])
        },
        onDisconnect: () => {
          ElMessage.warning('连接断开')
        }
      })
      stompClient.value.activate()
    }

    const selectUser = (user) => {
      currentUser.value = user
      loadChatHistory(user)
    }

    const loadChatHistory = async (user) => {
      // 加载聊天历史
      messages.value = []
    }

    const sendMessage = () => {
      if (!inputMessage.value.trim() || !currentUser.value) {
        ElMessage.warning('请选择用户并输入消息')
        return
      }

      const message = {
        fromUser: 'admin',
        toUser: currentUser.value,
        content: inputMessage.value,
        type: 1
      }

      if (stompClient.value && stompClient.value.connected) {
        stompClient.value.publish({
          destination: '/app/send',
          body: JSON.stringify(message)
        })
        messages.value.push({
          ...message,
          id: Date.now(),
          createTime: new Date()
        })
        inputMessage.value = ''
        scrollToBottom()
      } else {
        ElMessage.error('连接未建立')
      }
    }

    const scrollToBottom = () => {
      nextTick(() => {
        if (messagesRef.value) {
          messagesRef.value.scrollTop = messagesRef.value.scrollHeight
        }
      })
    }

    const formatTime = (time) => {
      if (!time) return ''
      const date = new Date(time)
      return date.toLocaleString('zh-CN')
    }

    return {
      messagesRef,
      messages,
      onlineUsers,
      currentUser,
      inputMessage,
      selectUser,
      sendMessage,
      formatTime
    }
  }
}
</script>

<style scoped>
.chat-container {
  display: flex;
  flex-direction: column;
  height: 600px;
}

.chat-messages {
  flex: 1;
  overflow-y: auto;
  padding: 20px;
  background: #f5f5f5;
  margin-bottom: 20px;
}

.message-item {
  margin-bottom: 15px;
}

.message-right {
  text-align: right;
}

.message-content {
  display: inline-block;
  padding: 10px 15px;
  background: #fff;
  border-radius: 4px;
  max-width: 70%;
  word-wrap: break-word;
}

.message-right .message-content {
  background: #409eff;
  color: #fff;
}

.message-time {
  font-size: 12px;
  color: #999;
  margin-top: 5px;
}

.chat-input {
  border-top: 1px solid #e4e7ed;
  padding-top: 10px;
}

.user-item {
  padding: 10px;
  cursor: pointer;
  border-bottom: 1px solid #e4e7ed;
}

.user-item:hover {
  background: #f5f7fa;
}

.user-item.active {
  background: #409eff;
  color: #fff;
}
</style>

