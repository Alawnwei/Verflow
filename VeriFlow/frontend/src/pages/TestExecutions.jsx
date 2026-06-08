import { useState, useEffect } from 'react'
import { Layout, Menu, Card, Typography, Button, Table, Space, Tag } from 'antd'
import { 
  FileTextOutlined, 
  ListOutlined, 
  CodeOutlined, 
  PlayCircleOutlined,
  LogoutOutlined,
  HomeOutlined,
  PlaySquareOutlined,
  RotateCcwOutlined,
  WrenchOutlined
} from '@ant-design/icons'

const { Header, Content, Sider } = Layout
const { Title } = Typography

function TestExecutions() {
  const [collapsed, setCollapsed] = useState(false)
  const [executions, setExecutions] = useState([])

  const menuItems = [
    { key: '/', icon: <HomeOutlined />, label: 'Dashboard' },
    { key: '/requirements', icon: <FileTextOutlined />, label: 'Requirements' },
    { key: '/test-cases', icon: <ListOutlined />, label: 'Test Cases' },
    { key: '/test-scripts', icon: <CodeOutlined />, label: 'Test Scripts' },
    { key: '/test-executions', icon: <PlayCircleOutlined />, label: 'Executions' },
  ]

  useEffect(() => {
    // Mock data
    setExecutions([
      { id: 'EXEC-001', scriptId: '1', executionType: 'SMOKE', status: 'PASSED', duration: '2.3s', retryCount: 0 },
      { id: 'EXEC-002', scriptId: '2', executionType: 'E2E', status: 'PASSED', duration: '5.1s', retryCount: 0 },
      { id: 'EXEC-003', scriptId: '3', executionType: 'REGRESSION', status: 'FAILED', duration: '1.2s', retryCount: 1, aiRepaired: true },
      { id: 'EXEC-004', scriptId: '4', executionType: 'SMOKE', status: 'PASSED', duration: '3.8s', retryCount: 0 },
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
    { title: 'Execution ID', dataIndex: 'id', key: 'id' },
    { title: 'Script ID', dataIndex: 'scriptId', key: 'scriptId' },
    { 
      title: 'Type', 
      dataIndex: 'executionType', 
      key: 'executionType',
      render: (type) => (
        <Tag color={
          type === 'SMOKE' ? 'blue' :
          type === 'E2E' ? 'purple' :
          type === 'REGRESSION' ? 'orange' : 'gray'
        }>
          {type}
        </Tag>
      )
    },
    { 
      title: 'Status', 
      dataIndex: 'status', 
      key: 'status',
      render: (status) => (
        <span className={`px-3 py-1 rounded-full text-sm font-medium ${
          status === 'PASSED' ? 'bg-green-100 text-green-700' :
          status === 'FAILED' ? 'bg-red-100 text-red-700' :
          'bg-gray-100 text-gray-700'
        }`}>
          {status}
        </span>
      )
    },
    { title: 'Duration', dataIndex: 'duration', key: 'duration' },
    { 
      title: 'AI Repaired', 
      dataIndex: 'aiRepaired', 
      key: 'aiRepaired',
      render: (val) => (
        <span className={val ? 'text-green-500' : 'text-gray-400'}>
          {val ? 'Yes' : '-'}
        </span>
      )
    },
    {
      title: 'Actions',
      key: 'actions',
      render: (_, record) => (
        <Space>
          <Button icon={<RotateCcwOutlined />} size="small" />
          {record.status === 'FAILED' && (
            <Button icon={<WrenchOutlined />} size="small">Repair & Retry</Button>
          )}
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
            defaultSelectedKeys={['/test-executions']}
            items={menuItems}
            onClick={handleMenuClick}
          />
        </Sider>
        
        <Layout style={{ padding: '24px' }}>
          <Content>
            <Space direction="vertical" className="w-full" size="large">
              <div className="flex items-center justify-between">
                <div>
                  <Title level={2}>Test Executions</Title>
                  <p className="text-gray-500">View execution history and orchestrate tests</p>
                </div>
                <Space>
                  <Button type="default" icon={<PlaySquareOutlined />}>Smoke Test</Button>
                  <Button type="default" icon={<PlaySquareOutlined />}>E2E Test</Button>
                  <Button type="default" icon={<PlaySquareOutlined />}>Regression</Button>
                  <Button type="primary" icon={<PlayCircleOutlined />}>Execute</Button>
                </Space>
              </div>
              
              <Card className="shadow-md">
                <Table 
                  columns={columns} 
                  dataSource={executions} 
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

export default TestExecutions
