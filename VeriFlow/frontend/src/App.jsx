import { BrowserRouter, Routes, Route } from 'react-router-dom'
import Login from './pages/Login'
import Dashboard from './pages/Dashboard'
import Requirements from './pages/Requirements'
import TestCases from './pages/TestCases'
import TestScripts from './pages/TestScripts'
import TestExecutions from './pages/TestExecutions'

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/" element={<Dashboard />} />
        <Route path="/requirements" element={<Requirements />} />
        <Route path="/test-cases" element={<TestCases />} />
        <Route path="/test-scripts" element={<TestScripts />} />
        <Route path="/test-executions" element={<TestExecutions />} />
      </Routes>
    </BrowserRouter>
  )
}

export default App
