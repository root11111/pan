/**
 * 密码加密工具
 * 与后端 PasswordUtil.encrypt() 保持一致
 * 使用 SHA-256 + Base64 编码
 */

/**
 * SHA-256 + Base64 加密密码
 * @param {string} password 原始密码
 * @returns {Promise<string>} 加密后的Base64字符串
 */
export async function encryptPassword(password) {
  // 使用 Web Crypto API 进行 SHA-256 哈希
  const encoder = new TextEncoder()
  const data = encoder.encode(password)
  const hashBuffer = await crypto.subtle.digest('SHA-256', data)
  
  // 将 ArrayBuffer 转换为 Uint8Array
  const hashArray = new Uint8Array(hashBuffer)
  
  // 转换为二进制字符串，然后使用 btoa 进行 Base64 编码
  // 这与 Java 的 Base64.getEncoder().encodeToString() 应该一致
  let binaryString = ''
  const len = hashArray.length
  for (let i = 0; i < len; i++) {
    binaryString += String.fromCharCode(hashArray[i])
  }
  
  // 使用 btoa 进行 Base64 编码
  return btoa(binaryString)
}

