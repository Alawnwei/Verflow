# AI驱动测试自动化平台 - API文档

## 1. 引言

### 1.1 文档目的
本文档定义AI驱动测试自动化平台的RESTful API接口规范。

### 1.2 基础路径
所有API的基础路径为：`https://api.example.com/v1`

### 1.3 认证方式
- 使用JWT Token认证
- Token放在请求头：`Authorization: Bearer {token}`

### 1.4 响应格式
所有接口响应均为JSON格式：

```json
{
  "code": 200,
  "message": "success",
  "data": {},
  "timestamp": "2024-01-15T10:30:00Z"
}
```

---

## 2. 需求管理接口

### 2.1 上传需求文档

**POST** `/api/requirements`

**请求体**：

| 字段 | 类型 | 必填 | 说明 |
|-----|------|-----|------|
| `title` | String | 是 | 文档标题 |
| `source_type` | String | 是 | 来源类型 |
| `content` | String | 是 | 文档内容 |
| `source_url` | String | 否 | 来源URL |
| `project_id` | String | 否 | 项目ID |

**成功响应**（201）：

```json
{
  "code": 201,
  "message": "created",
  "data": {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "title": "用户登录模块需求",
    "source_type": "BSD",
    "status": "pending",
    "created_at": "2024-01-15T10:30:00Z"
  }
}
```

### 2.2 获取需求列表

**GET** `/api/requirements`

**查询参数**：

| 参数 | 类型 | 必填 | 说明 |
|-----|------|-----|------|
| `page` | Integer | 否 | 页码，默认1 |
| `size` | Integer | 否 | 每页数量，默认20 |
| `status` | String | 否 | 状态过滤 |
| `source_type` | String | 否 | 来源类型过滤 |

**成功响应**（200）：

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "list": [...],
    "total": 100,
    "page": 1,
    "size": 20
  }
}
```

### 2.3 获取需求详情

**GET** `/api/requirements/{id}`

**路径参数**：

| 参数 | 类型 | 说明 |
|-----|------|------|
| `id` | String | 需求文档ID |

**成功响应**（200）：

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "title": "用户登录模块需求",
    "source_type": "BSD",
    "content": "...",
    "parsed_data": {...},
    "status": "analyzed",
    "created_at": "2024-01-15T10:30:00Z",
    "updated_at": "2024-01-15T10:35:00Z"
  }
}
```

### 2.4 触发AI解析

**POST** `/api/requirements/{id}/parse`

**路径参数**：

| 参数 | 类型 | 说明 |
|-----|------|------|
| `id` | String | 需求文档ID |

**成功响应**（200）：

```json
{
  "code": 200,
  "message": "parsing started",
  "data": {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "status": "parsing"
  }
}
```

### 2.5 删除需求文档

**DELETE** `/api/requirements/{id}`

**路径参数**：

| 参数 | 类型 | 说明 |
|-----|------|------|
| `id` | String | 需求文档ID |

**成功响应**（200）：

```json
{
  "code": 200,
  "message": "deleted",
  "data": {
    "success": true
  }
}
```

---

## 3. 测试用例接口

### 3.1 创建测试用例

**POST** `/api/test-cases`

**请求体**：

| 字段 | 类型 | 必填 | 说明 |
|-----|------|-----|------|
| `requirement_id` | String | 是 | 关联需求ID |
| `title` | String | 是 | 用例标题 |
| `type` | String | 是 | 用例类型 |
| `category` | String | 是 | 分类 |
| `priority` | String | 是 | 优先级 |
| `preconditions` | Array | 否 | 前置条件 |
| `test_data` | Object | 否 | 测试数据 |
| `test_steps` | Array | 是 | 测试步骤 |
| `expected_result` | String | 否 | 预期结果 |
| `postconditions` | Array | 否 | 后置条件 |
| `tags` | Array | 否 | 标签 |
| `mcp_config` | Object | 否 | MCP配置 |

**成功响应**（201）：

```json
{
  "code": 201,
  "message": "created",
  "data": {
    "id": "550e8400-e29b-41d4-a716-446655440001",
    "case_id": "TC-LOGIN-001",
    "title": "正常登录-邮箱密码正确",
    "status": "draft"
  }
}
```

### 3.2 获取用例列表

**GET** `/api/test-cases`

**查询参数**：

| 参数 | 类型 | 必填 | 说明 |
|-----|------|-----|------|
| `page` | Integer | 否 | 页码 |
| `size` | Integer | 否 | 每页数量 |
| `status` | String | 否 | 状态过滤 |
| `module` | String | 否 | 模块过滤 |
| `priority` | String | 否 | 优先级过滤 |
| `category` | String | 否 | 分类过滤 |

### 3.3 获取用例详情

**GET** `/api/test-cases/{id}`

### 3.4 更新测试用例

**PUT** `/api/test-cases/{id}`

**请求体**：同创建接口，字段均为可选

### 3.5 提交审核

**POST** `/api/test-cases/{id}/review`

**请求体**：

| 字段 | 类型 | 必填 | 说明 |
|-----|------|-----|------|
| `review_type` | String | 是 | 审核类型 |
| `status` | String | 是 | 审核状态 |
| `comments` | String | 否 | 审核意见 |

**成功响应**（200）：

```json
{
  "code": 200,
  "message": "review submitted",
  "data": {
    "case_id": "TC-LOGIN-001",
    "status": "reviewing"
  }
}
```

### 3.6 批量生成用例

**POST** `/api/test-cases/generate`

**请求体**：

| 字段 | 类型 | 必填 | 说明 |
|-----|------|-----|------|
| `requirement_id` | String | 是 | 需求ID |

**成功响应**（200）：

```json
{
  "code": 200,
  "message": "generated",
  "data": {
    "generated_count": 10,
    "case_ids": ["TC-LOGIN-001", "TC-LOGIN-002", ...]
  }
}
```

---

## 4. 测试脚本接口

### 4.1 创建脚本

**POST** `/api/scripts`

**请求体**：

| 字段 | 类型 | 必填 | 说明 |
|-----|------|-----|------|
| `case_id` | String | 是 | 关联用例ID |
| `framework` | String | 是 | 测试框架 |
| `code` | String | 是 | 脚本代码 |

**成功响应**（201）：

```json
{
  "code": 201,
  "message": "created",
  "data": {
    "id": "550e8400-e29b-41d4-a716-446655440002",
    "version": "v1.0",
    "status": "generated"
  }
}
```

### 4.2 获取脚本列表

**GET** `/api/scripts`

**查询参数**：

| 参数 | 类型 | 必填 | 说明 |
|-----|------|-----|------|
| `page` | Integer | 否 | 页码 |
| `size` | Integer | 否 | 每页数量 |
| `status` | String | 否 | 状态过滤 |
| `framework` | String | 否 | 框架过滤 |

### 4.3 获取脚本详情

**GET** `/api/scripts/{id}`

### 4.4 更新脚本

**PUT** `/api/scripts/{id}`

**请求体**：

| 字段 | 类型 | 必填 | 说明 |
|-----|------|-----|------|
| `code` | String | 是 | 更新后的代码 |
| `version` | String | 否 | 版本号 |

### 4.5 获取版本历史

**GET** `/api/scripts/{id}/versions`

**成功响应**（200）：

```json
{
  "code": 200,
  "message": "success",
  "data": [
    {
      "id": "v1.0",
      "version": "v1.0",
      "change_type": "create",
      "author": "AI",
      "created_at": "2024-01-15T10:30:00Z"
    },
    {
      "id": "v1.1",
      "version": "v1.1",
      "change_type": "edit",
      "author": "张三",
      "created_at": "2024-01-15T11:00:00Z"
    }
  ]
}
```

### 4.6 回滚版本

**POST** `/api/scripts/{id}/rollback`

**请求体**：

| 字段 | 类型 | 必填 | 说明 |
|-----|------|-----|------|
| `version` | String | 是 | 目标版本号 |

### 4.7 批量生成脚本

**POST** `/api/scripts/generate`

**请求体**：

| 字段 | 类型 | 必填 | 说明 |
|-----|------|-----|------|
| `case_ids` | Array | 是 | 用例ID列表 |
| `framework` | String | 是 | 测试框架 |

---

## 5. 测试执行接口

### 5.1 执行测试

**POST** `/api/executions`

**请求体**：

| 字段 | 类型 | 必填 | 说明 |
|-----|------|-----|------|
| `script_ids` | Array | 是 | 脚本ID列表 |
| `execution_type` | String | 是 | 执行类型 |
| `environment` | String | 否 | 执行环境 |

**成功响应**（200）：

```json
{
  "code": 200,
  "message": "executing",
  "data": {
    "execution_ids": ["550e8400-e29b-41d4-a716-446655440003"]
  }
}
```

### 5.2 获取执行列表

**GET** `/api/executions`

**查询参数**：

| 参数 | 类型 | 必填 | 说明 |
|-----|------|-----|------|
| `page` | Integer | 否 | 页码 |
| `size` | Integer | 否 | 每页数量 |
| `status` | String | 否 | 状态过滤 |
| `execution_type` | String | 否 | 执行类型过滤 |

### 5.3 获取执行详情

**GET** `/api/executions/{id}`

**成功响应**（200）：

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "id": "550e8400-e29b-41d4-a716-446655440003",
    "script_id": "550e8400-e29b-41d4-a716-446655440002",
    "execution_type": "smoke",
    "status": "passed",
    "duration": 5,
    "retry_count": 0,
    "ai_repaired": false,
    "created_at": "2024-01-15T10:35:00Z"
  }
}
```

### 5.4 重试执行

**POST** `/api/executions/{id}/retry`

### 5.5 AI修复

**POST** `/api/executions/{id}/repair`

**成功响应**（200）：

```json
{
  "code": 200,
  "message": "repairing",
  "data": {
    "execution_id": "550e8400-e29b-41d4-a716-446655440003",
    "repair_status": "attempting"
  }
}
```

---

## 6. 测试套件接口

### 6.1 创建套件

**POST** `/api/suites`

**请求体**：

| 字段 | 类型 | 必填 | 说明 |
|-----|------|-----|------|
| `name` | String | 是 | 套件名称 |
| `description` | String | 否 | 套件描述 |
| `type` | String | 是 | 套件类型 |
| `script_ids` | Array | 是 | 脚本ID列表 |
| `schedule_cron` | String | 否 | 定时Cron |

### 6.2 获取套件列表

**GET** `/api/suites`

### 6.3 获取套件详情

**GET** `/api/suites/{id}`

### 6.4 执行套件

**POST** `/api/suites/{id}/execute`

---

## 7. 报告接口

### 7.1 获取报告列表

**GET** `/api/reports`

**查询参数**：

| 参数 | 类型 | 必填 | 说明 |
|-----|------|-----|------|
| `start_date` | String | 是 | 开始日期 |
| `end_date` | String | 是 | 结束日期 |

### 7.2 获取报告详情

**GET** `/api/reports/{id}`

**成功响应**（200）：

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "id": "550e8400-e29b-41d4-a716-446655440004",
    "name": "2024-01-15 冒烟测试报告",
    "total_cases": 10,
    "passed_count": 9,
    "failed_count": 1,
    "skipped_count": 0,
    "pass_rate": 0.9,
    "generated_at": "2024-01-15T10:40:00Z"
  }
}
```

### 7.3 获取统计摘要

**GET** `/api/reports/summary`

**查询参数**：

| 参数 | 类型 | 必填 | 说明 |
|-----|------|-----|------|
| `start_date` | String | 是 | 开始日期 |
| `end_date` | String | 是 | 结束日期 |

**成功响应**（200）：

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "total_executions": 100,
    "total_cases": 500,
    "passed_count": 450,
    "failed_count": 50,
    "pass_rate": 0.9,
    "avg_duration": 15,
    "ai_repair_success_count": 30
  }
}
```

---

## 8. 用户管理接口

### 8.1 登录

**POST** `/api/auth/login`

**请求体**：

| 字段 | 类型 | 必填 | 说明 |
|-----|------|-----|------|
| `username` | String | 是 | 用户名 |
| `password` | String | 是 | 密码 |

**成功响应**（200）：

```json
{
  "code": 200,
  "message": "success",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "user": {
      "id": "550e8400-e29b-41d4-a716-446655440005",
      "username": "admin",
      "role": "admin"
    }
  }
}
```

### 8.2 获取当前用户

**GET** `/api/auth/me`

---

## 9. 错误响应格式

所有错误响应遵循统一格式：

```json
{
  "code": 400,
  "message": "Bad Request",
  "error": {
    "code": "VALIDATION_ERROR",
    "message": "参数校验失败",
    "details": [
      {
        "field": "title",
        "message": "标题不能为空"
      }
    ]
  },
  "timestamp": "2024-01-15T10:30:00Z"
}
```

### 错误码列表

| HTTP状态码 | 错误码 | 说明 |
|-----------|-------|------|
| 400 | VALIDATION_ERROR | 参数校验失败 |
| 401 | UNAUTHORIZED | 未认证 |
| 403 | FORBIDDEN | 无权限 |
| 404 | NOT_FOUND | 资源不存在 |
| 500 | INTERNAL_ERROR | 服务器内部错误 |

---

**文档版本**: v1.0  
**创建日期**: 2026年5月  
**作者**: Alan
**审核状态**: 待审核