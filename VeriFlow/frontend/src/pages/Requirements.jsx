import { useState, useEffect } from 'react'
import { Layout, Menu, Card, Typography, Button, Table, Modal, Form, Input, Select, Space } from 'antd'
import { 
  FileTextOutlined, 
  ListOutlined, 
  CodeOutlined, 
  PlayCircleOutlined,
  LogoutOutlined,
  HomeOutlined,
  PlusOutlined,
  EditOutlined,
  TrashOutlined,
  EyeOutlined
} from '@ant-design/icons'

const { Header, Content, Sider } = Layout
const { Title } = Typography
const { Option } = Select

function Requirements() {
  const [collapsed, setCollapsed] = useState(false)
  const [requirements, setRequirements] = useState([])
  const [isModalVisible, setIsModalVisible] = useState(false)
  const [form] = Form.useForm()
  const [editingId, setEditingId] = useState(null)

  const menuItems = [
    { key: '/', icon: <HomeOutlined />, label: 'Dashboard' },
    { key: '/requirements', icon: <FileTextOutlined />, label: 'Requirements' },
    { key: '/test-cases', icon: <ListOutlined />, label: 'Test Cases' },
    { key: '/test-scripts', icon: <CodeOutlined />, label: 'Test Scripts' },
    { key: '/test-executions', icon: <PlayCircleOutlined />, label: 'Executions' },
  ]

  useEffect(() => {
    // Mock data
    setRequirements([
      { id: '1', title: 'User Login Feature', sourceType: 'BSD', status: 'PARSED', createdAt: '2024-01-15' },
      { id: '2', title: 'Dashboard Analytics', sourceType: 'JIRA', status: 'ANALYZED', createdAt: '2024-01-14' },
      { id: '3', title: 'Report Generation', sourceType: 'LARK', status: 'PENDING', createdAt: '2024-01-13' },
      { id: '4', title: 'API Integration', sourceType: 'MARKDOWN', status: 'PARSED', createdAt: '2024-01-12' },
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

  const showModal = (requirement = null) => {
    if (requirement) {
      setEditingId(requirement.id)
      form.setFieldsValue(requirement)
    } else {
      setEditingId(null)
      form.resetFields()
    }
    setIsModalVisible(true)
  }

  const handleOk = () => {
    form.validateFields().then((values) => {
      console.log('Form values:', values)
      setIsModalVisible(false)
      form.resetFields()
    })
  }

  const handleDelete = (id) => {
    setRequirements(prev => prev.filter(r => r.id !== id))
  }

  const columns = [
    { title: 'Title', dataIndex: 'title', key: 'title' },
    { title: 'Source Type', dataIndex: 'sourceType', key: 'sourceType' },
    { 
      title: 'Status', 
      dataIndex: 'status', 
      key: 'status',
      render: (status) => (
        <span className={`px-2 py-1 rounded text-xs font-medium ${
          status === 'PARSED' ? 'bg-blue-100 text-blue-700' :
          status === 'ANALYZED' ? 'bg-green-100 text-green-700' :
          'bg-gray-100 text-gray-700'
        }`}>
          {status}
        </span>
      )
    },
    { title: 'Created At', dataIndex: 'createdAt', key: 'createdAt' },
    {
      title: 'Actions',
      key: 'actions',
      render: (_, record) => (
        <Space>
          <Button icon={<EyeOutlined />} size="small" />
          <Button icon={<EditOutlined />} size="small" onClick={() => showModal(record)} />
          <Button icon={<TrashOutlined />} size="small" danger onClick={() => handleDelete(record.id)} />
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
            defaultSelectedKeys={['/requirements']}
            items={menuItems}
            onClick={handleMenuClick}
          />
        </Sider>
        
        <Layout style={{ padding: '24px' }}>
          <Content>
            <Space direction="vertical" className="w-full" size="large">
              <div className="flex items-center justify-between">
                <div>
                  <Title level={2}>Requirements</Title>
                  <p className="text-gray-500">Manage your test requirements from various sources</p>
                </div>
                <Button type="primary" icon={<PlusOutlined />}>
                  New Requirement
                </Button>
              </div>
              
              <Card className="shadow-md">
                <Table 
                  columns={columns} 
                  dataSource={requirements} 
                  rowKey="id"
                  pagination={{ pageSize: 10 }}
                />
              </Card>
            </Space>
          </Content>
        </Layout>
      </Layout>

      <Modal
        title={editingId ? 'Edit Requirement' : 'Create Requirement'}
        visible={isModalVisible}
        onOk={handleOk}
        onCancel={() => setIsModalVisible(false)}
      >
        <Form form={form} layout="vertical">
          <Form.Item name="title" label="Title" rules={[{ required: true }]}>
            <Input />
          </Form.Item>
          <Form.Item name="sourceType" label="Source Type" rules={[{ required: true }]}>
            <Select>
              <Option value="BSD">BSD</Option>
              <Option value="JIRA">JIRA</Option>
              <Option value="LARK">Lark</Option>
              <Option value="MARKDOWN">Markdown</Option>
            </Select>
          </Form.Item>
          <Form.Item name="content" label="Content">
            <Input.TextArea rows={4} />
          </Form.Item>
        </Form>
      </Modal>
    </Layout>
  )
}

export default Requirements
