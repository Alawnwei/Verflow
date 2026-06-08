import { useState } from 'react'
import { Form, Input, Button, Card, Typography, Space } from 'antd'
import { LockOutlined, UserOutlined } from '@ant-design/icons'

const { Title } = Typography

function Login() {
  const [form] = Form.useForm()
  const [loading, setLoading] = useState(false)

  const onFinish = (values) => {
    setLoading(true)
    console.log('Login values:', values)
    
    // Mock login success
    setTimeout(() => {
      setLoading(false)
      localStorage.setItem('token', 'mock-token')
      window.location.href = '/'
    }, 1000)
  }

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 to-indigo-100 flex items-center justify-center p-4">
      <Card className="w-full max-w-md shadow-xl">
        <Space direction="vertical" className="w-full" size="large">
          <div className="text-center">
            <Title level={2} className="text-primary-600">VeriFlow</Title>
            <p className="text-gray-500">AI-Driven Test Automation Platform</p>
          </div>
          
          <Form
            form={form}
            name="login"
            onFinish={onFinish}
            layout="vertical"
          >
            <Form.Item
              name="email"
              label="Email"
              rules={[
                { required: true, message: 'Please input your email!' },
                { type: 'email', message: 'Please enter a valid email!' }
              ]}
            >
              <Input 
                prefix={<UserOutlined className="text-gray-400" />} 
                placeholder="test@example.com"
                defaultValue="test@example.com"
              />
            </Form.Item>

            <Form.Item
              name="password"
              label="Password"
              rules={[
                { required: true, message: 'Please input your password!' }
              ]}
            >
              <Input.Password
                prefix={<LockOutlined className="text-gray-400" />}
                placeholder="123456"
                defaultValue="123456"
              />
            </Form.Item>

            <Form.Item>
              <Button 
                type="primary" 
                htmlType="submit" 
                className="w-full h-12 text-base"
                loading={loading}
              >
                {loading ? 'Logging in...' : 'Login'}
              </Button>
            </Form.Item>
          </Form>

          <div className="text-center text-gray-400 text-sm">
            Test credentials: test@example.com / 123456
          </div>
        </Space>
      </Card>
    </div>
  )
}

export default Login
