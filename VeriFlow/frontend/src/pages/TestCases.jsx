import { useState, useEffect } from 'react'
import { Layout, Menu, Card, Typography, Button, Table, Space } from 'antd'
import { 
  FileTextOutlined, 
  ListOutlined, 
  CodeOutlined, 
  PlayCircleOutlined,
  LogoutOutlined,
  HomeOutlined,
  PlusOutlined,
  EyeOutlined,
  GenerateOutlined
} from '@ant-design/icons'

const { Header, Content, Sider } = Layout
const { Title } = Typography

function TestCases() {
  const [collapsed, setCollapsed] = useState(false)
  const [testCases, setTestCases] = useState([])

  const menuItems = [
    { key: '/', icon: <HomeOutlined />, label: 'Dashboard' },
    { key: '/requirements', icon: <FileTextOutlined />, label: 'Requirements' },
    { key: '/test-cases', icon: <ListOutlined />, label: 'Test Cases' },
    { key: '/test-scripts', icon: <CodeOutlined />, label: 'Test Scripts' },
    { key: '/test-executions', icon: <PlayCircleOutlined />, label: 'Executions' },
  ]

  useEffect(() => {
    // Mock data
    setTestCases([
      { id: '1', caseId: 'TC-FUN-001', title: 'Normal Login', module: 'User Login', priority: 'P0', status: 'APPROVED' },
      { id: '2', caseId: 'TC-FUN-002', title: 'Invalid Password', module: 'User Login', priority: 'P1', status: 'REVIEWING' },
      { id: '3', caseId: 'TC-E2E-001', title: 'Order Checkout', module: 'Order', priority: 'P0', status: 'DRAFT' },
      { id: '4', caseId: 'TC-SMK-001', title: 'Homepage Load', module: 'Homepage', priority: 'P1', status: 'APPROVED' },
    ])
  }, [])

  const handleMenuClick = ({ key }) => {
    if (key === 'logout') {
      localStorage.removeItem('token')
      window.location.href = '/login'
      return
    }
    window.location.href = key
  }

  const columns = [
    { title: 'Case ID', dataIndex: 'caseId', key: 'caseId' },
    { title: 'Title', dataIndex: 'title', key: 'title' },
    { title: 'Module', dataIndex: 'module', key: 'module' },
    { 
      title: 'Priority', 
      dataIndex: 'priority', 
      key: 'priority',
      render: (priority) => (
        <span className={`px-2 py-1 rounded text-xs font-medium ${
          priority === 'P0' ? 'bg-red-100 text-red-700' :
          priority === 'P1' ? 'bg-yellow-100 text-yellow-700' :
          'bg-gray-100 text-gray-700'
        }`}>
          {priority}
        </span>
      )
    },
    { 
      title: 'Status', 
      dataIndex: 'status', 
      key: 'status',
      render: (status) => (
        <span className={`px-2 py-1 rounded text-xs font-medium ${
          status === 'APPROVED' ? 'bg-green-100 text-green-700' :
          status === 'REVIEWING' ? 'bg-blue-100 text-blue-700' :
          'bg-gray-100 text-gray-700'
        }`}>
          {status}
        </span>
      )
    },
    {
      title: 'Actions',
      key: 'actions',
      render: (_, record) => (
        <Space>
          <Button icon={<EyeOutlined />} size="small" />
          <Button icon={<GenerateOutlined />} size="small">Generate Script</Button>
        </Space>
      ),
    },
  ]

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
            defaultSelectedKeys={['/test-cases']}
            items={menuItems}
            onClick={handleMenuClick}
          />
        </Sider>
        
        <Layout style={{ padding: '24px' }}>
          <Content>
            <Space direction="vertical" className="w-full" size="large">
              <div className="flex items-center justify-between">
                <div>
                  <Title level={2}>Test Cases</Title>
                  <p className="text-gray-500">Manage and review AI-generated test cases</p>
                </div>
                <Space>
                  <Button type="default" icon={<GenerateOutlined />}>
                    Generate from Requirement
                  </Button>
                  <Button type="primary" icon={<PlusOutlined />}>
                    New Test Case
                  </Button>
                </Space>
              </div>
              
              <Card className="shadow-md">
                <Table 
                  columns={columns} 
                  dataSource={testCases} 
                  rowKey="id"
                  pagination={{ pageSize: 10 }}
                />
              </Card>
            </Space>
          </Content>
        </Layout>
      </Layout>
    </Layout>
  )
}

export default TestCases
