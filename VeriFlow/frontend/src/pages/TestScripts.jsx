import { useState, useEffect } from 'react'
import { Layout, Menu, Card, Typography, Button, Table, Space, Modal, Code } from 'antd'
import { 
  FileTextOutlined, 
  ListOutlined, 
  CodeOutlined, 
  PlayCircleOutlined,
  LogoutOutlined,
  HomeOutlined,
  EyeOutlined,
  EditOutlined,
  PlaySquareOutlined,
  WrenchOutlined
} from '@ant-design/icons'

const { Header, Content, Sider } = Layout
const { Title } = Typography

function TestScripts() {
  const [collapsed, setCollapsed] = useState(false)
  const [scripts, setScripts] = useState([])
  const [selectedScript, setSelectedScript] = useState(null)
  const [isModalVisible, setIsModalVisible] = useState(false)

  const menuItems = [
    { key: '/', icon: <HomeOutlined />, label: 'Dashboard' },
    { key: '/requirements', icon: <FileTextOutlined />, label: 'Requirements' },
    { key: '/test-cases', icon: <ListOutlined />, label: 'Test Cases' },
    { key: '/test-scripts', icon: <CodeOutlined />, label: 'Test Scripts' },
    { key: '/test-executions', icon: <PlayCircleOutlined />, label: 'Executions' },
  ]

  useEffect(() => {
    // Mock data
    setScripts([
      { id: '1', caseId: 'TC-FUN-001', framework: 'PLAYWRIGHT', status: 'REVIEWED', isAiGenerated: true },
      { id: '2', caseId: 'TC-FUN-002', framework: 'PLAYWRIGHT', status: 'GENERATED', isAiGenerated: true },
      { id: '3', caseId: 'TC-E2E-001', framework: 'SELENIUM', status: 'EDITED', isAiGenerated: false },
      { id: '4', caseId: 'TC-SMK-001', framework: 'PLAYWRIGHT', status: 'REVIEWED', isAiGenerated: true },
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

  const viewScript = (script) => {
    setSelectedScript({
      ...script,
      code: `const { test, expect } = require('@playwright/test');\n\ntest('${script.caseId}', async ({ page }) => {\n    await page.goto('https://admin.example.com/login');\n    await page.fill('input[name=\"email\"]', 'test@example.com');\n    await page.fill('input[name=\"password\"]', '123456');\n    await page.click('button[type=\"submit\"]');\n    await expect(page).toHaveURL('https://admin.example.com/dashboard');\n});`
    })
    setIsModalVisible(true)
  }

  const columns = [
    { title: 'Case ID', dataIndex: 'caseId', key: 'caseId' },
    { title: 'Framework', dataIndex: 'framework', key: 'framework' },
    { 
      title: 'AI Generated', 
      dataIndex: 'isAiGenerated', 
      key: 'isAiGenerated',
      render: (val) => (
        <span className={val ? 'text-green-500' : 'text-gray-500'}>
          {val ? 'Yes' : 'No'}
        </span>
      )
    },
    { 
      title: 'Status', 
      dataIndex: 'status', 
      key: 'status',
      render: (status) => (
        <span className={`px-2 py-1 rounded text-xs font-medium ${
          status === 'REVIEWED' ? 'bg-green-100 text-green-700' :
          status === 'GENERATED' ? 'bg-blue-100 text-blue-700' :
          status === 'EDITED' ? 'bg-yellow-100 text-yellow-700' :
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
          <Button icon={<EyeOutlined />} size="small" onClick={() => viewScript(record)} />
          <Button icon={<EditOutlined />} size="small" />
          <Button icon={<PlaySquareOutlined />} size="small" />
          <Button icon={<WrenchOutlined />} size="small">Repair</Button>
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
            defaultSelectedKeys={['/test-scripts']}
            items={menuItems}
            onClick={handleMenuClick}
          />
        </Sider>
        
        <Layout style={{ padding: '24px' }}>
          <Content>
            <Space direction="vertical" className="w-full" size="large">
              <div className="flex items-center justify-between">
                <div>
                  <Title level={2}>Test Scripts</Title>
                  <p className="text-gray-500">View and manage AI-generated test scripts</p>
                </div>
              </div>
              
              <Card className="shadow-md">
                <Table 
                  columns={columns} 
                  dataSource={scripts} 
                  rowKey="id"
                  pagination={{ pageSize: 10 }}
                />
              </Card>
            </Space>
          </Content>
        </Layout>
      </Layout>

      <Modal
        title={`Script: ${selectedScript?.caseId}`}
        visible={isModalVisible}
        onCancel={() => setIsModalVisible(false)}
        width={800}
      >
        <Code className="text-sm">
          {selectedScript?.code}
        </Code>
      </Modal>
    </Layout>
  )
}

export default TestScripts
