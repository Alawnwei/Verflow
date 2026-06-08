import { Layout, Menu, Card, Typography, Row, Col, Space } from 'antd'
import { 
  BarChartOutlined, 
  FileTextOutlined, 
  ListOutlined, 
  CodeOutlined, 
  PlayCircleOutlined,
  LogoutOutlined,
  HomeOutlined
} from '@ant-design/icons'
import { useState } from 'react'

const { Header, Content, Sider } = Layout
const { Title, Text } = Typography

function Dashboard() {
  const [collapsed, setCollapsed] = useState(false)

  const menuItems = [
    { key: '/', icon: <HomeOutlined />, label: 'Dashboard' },
    { key: '/requirements', icon: <FileTextOutlined />, label: 'Requirements' },
    { key: '/test-cases', icon: <ListOutlined />, label: 'Test Cases' },
    { key: '/test-scripts', icon: <CodeOutlined />, label: 'Test Scripts' },
    { key: '/test-executions', icon: <PlayCircleOutlined />, label: 'Executions' },
  ]

  const stats = [
    { title: 'Requirements', value: 128, icon: <FileTextOutlined className="text-blue-500" />, trend: '+12%' },
    { title: 'Test Cases', value: 856, icon: <ListOutlined className="text-green-500" />, trend: '+8%' },
    { title: 'Test Scripts', value: 642, icon: <CodeOutlined className="text-purple-500" />, trend: '+15%' },
    { title: 'Executions', value: 3240, icon: <PlayCircleOutlined className="text-orange-500" />, trend: '+20%' },
  ]

  const recentExecutions = [
    { id: 'EXEC-001', script: 'Login Test', status: 'PASSED', duration: '2.3s', time: '2 min ago' },
    { id: 'EXEC-002', script: 'Dashboard Test', status: 'PASSED', duration: '5.1s', time: '5 min ago' },
    { id: 'EXEC-003', script: 'API Test', status: 'FAILED', duration: '1.2s', time: '8 min ago' },
    { id: 'EXEC-004', script: 'User Management', status: 'PASSED', duration: '3.8s', time: '15 min ago' },
  ]

  const handleMenuClick = ({ key }) => {
    if (key === 'logout') {
      localStorage.removeItem('token')
      window.location.href = '/login'
      return
    }
    window.location.href = key
  }

  return (
    <Layout style={{ minHeight: '100vh' }}>
      <Header className="bg-primary-600 flex items-center justify-between px-6">
        <Title level={3} className="text-white mb-0">VeriFlow</Title>
        <Button 
          type="text" 
          icon={<LogoutOutlined />} 
          className="text-white hover:bg-primary-700"
          onClick={() => {
            localStorage.removeItem('token')
            window.location.href = '/login'
          }}
        >
          Logout
        </Button>
      </Header>
      
      <Layout>
        <Sider 
          collapsible 
          collapsed={collapsed} 
          onCollapse={setCollapsed}
          className="bg-gray-800"
        >
          <Menu
            mode="inline"
            theme="dark"
            defaultSelectedKeys={['/']}
            items={menuItems}
            onClick={handleMenuClick}
          />
        </Sider>
        
        <Layout style={{ padding: '24px' }}>
          <Content>
            <Space direction="vertical" className="w-full" size="large">
              <div>
                <Title level={2}>Dashboard</Title>
                <Text className="text-gray-500">Welcome to VeriFlow - AI-Driven Test Automation Platform</Text>
              </div>
              
              <Row gutter={16}>
                {stats.map((stat) => (
                  <Col span={6} key={stat.title}>
                    <Card className="shadow-md">
                      <div className="flex items-center justify-between">
                        <div>
                          <Text className="text-gray-500">{stat.title}</Text>
                          <Title level={2} className="mt-2">{stat.value}</Title>
                        </div>
                        <div className="text-4xl">
                          {stat.icon}
                        </div>
                      </div>
                      <div className="mt-4 text-green-500 text-sm">{stat.trend} this week</div>
                    </Card>
                  </Col>
                ))}
              </Row>
              
              <Card title="Recent Executions" className="shadow-md">
                <table className="w-full">
                  <thead>
                    <tr className="text-gray-500 text-sm">
                      <th className="text-left py-3">Execution ID</th>
                      <th className="text-left py-3">Script</th>
                      <th className="text-left py-3">Status</th>
                      <th className="text-left py-3">Duration</th>
                      <th className="text-left py-3">Time</th>
                    </tr>
                  </thead>
                  <tbody>
                    {recentExecutions.map((execution) => (
                      <tr key={execution.id} className="border-t border-gray-100">
                        <td className="py-3">{execution.id}</td>
                        <td className="py-3 font-medium">{execution.script}</td>
                        <td className="py-3">
                          <span className={`px-2 py-1 rounded text-xs font-medium ${
                            execution.status === 'PASSED' 
                              ? 'bg-green-100 text-green-700' 
                              : 'bg-red-100 text-red-700'
                          }`}>
                            {execution.status}
                          </span>
                        </td>
                        <td className="py-3">{execution.duration}</td>
                        <td className="py-3 text-gray-500">{execution.time}</td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </Card>
            </Space>
          </Content>
        </Layout>
      </Layout>
    </Layout>
  )
}

export default Dashboard
