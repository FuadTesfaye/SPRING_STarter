# Implementation Plan - Toobuy E-Commerce Platform
**Date:** May 14, 2026
**Priority:** CRITICAL
**Estimated Completion:** 8-12 hours

---

## Phase 1: Critical Backend Fixes (Priority 1)
**Timeline:** 2-3 hours
**Goal:** Restore all backend services to healthy status

### Task 1.1: Fix Auth Service Database Configuration
**Status:** ❌ Not Started
**Priority:** CRITICAL
**Estimated Time:** 30 minutes

**Steps:**
1. Check auth-service application.yml configuration
2. Ensure Supabase connection properties are set
3. Add missing environment variables to docker-compose.yml
4. Test auth-service startup
5. Verify health endpoint returns healthy

**Files to Modify:**
- `Backend/auth-service/src/main/resources/application.yml`
- `Backend/docker-compose.yml`

**Commands:**
```bash
# Check current config
cat Backend/auth-service/src/main/resources/application.yml

# Restart auth-service after fix
docker-compose restart auth-service
docker logs auth-service --tail 50
```

**Success Criteria:**
- Auth service starts without errors
- Health check returns healthy
- Can access /actuator/health endpoint

---

### Task 1.2: Fix Missing Use Case Beans - Order Service
**Status:** ❌ Not Started
**Priority:** CRITICAL
**Estimated Time:** 45 minutes

**Steps:**
1. Locate CreateOrderUseCase class
2. Add @Service annotation
3. Check constructor injection
4. Verify all dependencies are annotated
5. Restart order-service
6. Verify health endpoint

**Files to Modify:**
- `Backend/order-service/src/main/java/com/ecommerce/order/application/usecase/CreateOrderUseCase.java`
- Other use case classes in same directory

**Code Changes Required:**
```java
@Service
public class CreateOrderUseCase {
    private final OrderRepository orderRepository;
    private final OrderEventPublisher eventPublisher;
    
    public CreateOrderUseCase(OrderRepository orderRepository, 
                              OrderEventPublisher eventPublisher) {
        this.orderRepository = orderRepository;
        this.eventPublisher = eventPublisher;
    }
    // ... rest of implementation
}
```

**Success Criteria:**
- Order service starts without errors
- No "No qualifying bean" errors
- Health check returns healthy

---

### Task 1.3: Fix Missing Use Case Beans - Payment Service
**Status:** ❌ Not Started
**Priority:** CRITICAL
**Estimated Time:** 30 minutes

**Steps:**
1. Locate ProcessPaymentUseCase class
2. Add @Service annotation
3. Check constructor injection
4. Verify all dependencies are annotated
5. Restart payment-service
6. Verify health endpoint

**Files to Modify:**
- `Backend/payment-service/src/main/java/com/ecommerce/payment/application/usecase/ProcessPaymentUseCase.java`

**Success Criteria:**
- Payment service starts without errors
- Health check returns healthy

---

### Task 1.4: Fix Inventory Service Use Cases
**Status:** ❌ Not Started
**Priority:** CRITICAL
**Estimated Time:** 30 minutes

**Steps:**
1. Check inventory-service for similar issues
2. Add @Service annotations to all use cases
3. Restart inventory-service
4. Verify health endpoint

**Files to Check:**
- `Backend/inventory-service/src/main/java/com/ecommerce/inventory/application/usecase/`

**Success Criteria:**
- Inventory service starts without errors
- Health check returns healthy

---

### Task 1.5: Fix Shipping Service Use Cases
**Status:** ❌ Not Started
**Priority:** CRITICAL
**Estimated Time:** 30 minutes

**Steps:**
1. Check shipping-service for similar issues
2. Add @Service annotations to all use cases
3. Restart shipping-service
4. Verify health endpoint

**Files to Check:**
- `Backend/shipping-service/src/main/java/com/ecommerce/shipping/application/usecase/`

**Success Criteria:**
- Shipping service starts without errors
- Health check returns healthy

---

### Task 1.6: Fix Notification Service
**Status:** ❌ Not Started
**Priority:** HIGH
**Estimated Time:** 30 minutes

**Steps:**
1. Check notification-service logs
2. Identify health check failure reason
3. Fix configuration or code issues
4. Restart notification-service
5. Verify health endpoint

**Success Criteria:**
- Notification service starts without errors
- Health check returns healthy

---

## Phase 2: Database Schema Initialization (Priority 1)
**Timeline:** 1-2 hours
**Goal:** Initialize database schemas and tables

### Task 2.1: Create Database Schemas
**Status:** ❌ Not Started
**Priority:** CRITICAL
**Estimated Time:** 30 minutes

**Steps:**
1. Connect to PostgreSQL database
2. Create schemas for each service
3. Verify schema creation

**SQL Commands:**
```sql
-- Connect to database
docker exec -it ecommerce-postgres psql -U postgres -d ecommerce

-- Create schemas
CREATE SCHEMA IF NOT EXISTS auth_schema;
CREATE SCHEMA IF NOT EXISTS order_schema;
CREATE SCHEMA IF NOT EXISTS payment_schema;
CREATE SCHEMA IF NOT EXISTS inventory_schema;
CREATE SCHEMA IF NOT EXISTS shipping_schema;
CREATE SCHEMA IF NOT EXISTS notification_schema;

-- Verify schemas
SELECT schema_name FROM information_schema.schemata 
WHERE schema_name LIKE '%_schema' 
ORDER BY schema_name;
```

**Success Criteria:**
- All 6 schemas created successfully
- No errors in schema creation

---

### Task 2.2: Update Service Configurations for Schema Usage
**Status:** ❌ Not Started
**Priority:** CRITICAL
**Estimated Time:** 45 minutes

**Steps:**
1. Update each service's application.yml
2. Set default schema for each service
3. Configure Hibernate DDL auto
4. Restart all services

**Configuration Template:**
```yaml
spring:
  datasource:
    url: jdbc:postgresql://postgres:5432/ecommerce?currentSchema=order_schema
    username: postgres
    password: postgres
  jpa:
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
        default_schema: order_schema
        dialect: org.hibernate.dialect.PostgreSQLDialect
```

**Files to Modify:**
- `Backend/order-service/src/main/resources/application.yml`
- `Backend/payment-service/src/main/resources/application.yml`
- `Backend/inventory-service/src/main/resources/application.yml`
- `Backend/shipping-service/src/main/resources/application.yml`
- `Backend/notification-service/src/main/resources/application.yml`

**Success Criteria:**
- All services connect to correct schemas
- Tables created in correct schemas
- No schema-related errors in logs

---

### Task 2.3: Verify Table Creation
**Status:** ❌ Not Started
**Priority:** HIGH
**Estimated Time:** 15 minutes

**Steps:**
1. Check each schema for tables
2. Verify table structures
3. Ensure foreign keys are correct

**SQL Commands:**
```sql
-- Check tables in each schema
SELECT table_schema, table_name 
FROM information_schema.tables 
WHERE table_schema LIKE '%_schema'
ORDER BY table_schema, table_name;

-- Check specific schema
\d order_schema.orders
```

**Success Criteria:**
- All expected tables created
- Table structures match entity definitions
- No missing foreign keys

---

## Phase 3: Integration Testing (Priority 2)
**Timeline:** 2-3 hours
**Goal:** Test all user flows end-to-end

### Task 3.1: Test Authentication Flow
**Status:** ❌ Not Started
**Priority:** HIGH
**Estimated Time:** 30 minutes

**Steps:**
1. Navigate to frontend login page
2. Test user registration
3. Test user login
4. Verify JWT token storage
5. Test logout functionality
6. Test protected route access

**Test Cases:**
- Register new user
- Login with valid credentials
- Login with invalid credentials
- Access protected page without token
- Access protected page with valid token
- Logout and clear token

**Success Criteria:**
- All authentication flows work correctly
- JWT tokens properly stored and validated
- Protected routes enforce authentication

---

### Task 3.2: Test Product Catalog Flow
**Status:** ❌ Not Started
**Priority:** HIGH
**Estimated Time:** 30 minutes

**Steps:**
1. Navigate to products page
2. Verify products load from backend
3. Test product filtering
4. Test product search
5. Test category filtering
6. Navigate to product detail page

**Test Cases:**
- Load all products
- Load products by category
- Search products by name
- View product details
- Check product images load

**Success Criteria:**
- Products load correctly from inventory service
- Filtering and search work
- Product details display correctly

---

### Task 3.3: Test Shopping Cart Flow
**Status:** ❌ Not Started
**Priority:** HIGH
**Estimated Time:** 30 minutes

**Steps:**
1. Add product to cart
2. Update quantity
3. Remove product from cart
4. Clear cart
5. Verify cart persistence
6. Check cart total calculation

**Test Cases:**
- Add single product
- Add multiple products
- Update quantity
- Remove product
- Clear entire cart
- Calculate totals correctly

**Success Criteria:**
- Cart operations work correctly
- Cart persists across page refreshes
- Totals calculated accurately

---

### Task 3.4: Test Checkout Flow
**Status:** ❌ Not Started
**Priority:** HIGH
**Estimated Time:** 45 minutes

**Steps:**
1. Navigate to checkout from cart
2. Fill shipping information
3. Fill payment information
4. Review order
5. Submit order
6. Verify order creation
7. Verify payment processing

**Test Cases:**
- Complete checkout with valid data
- Validate required fields
- Test payment processing
- Verify order creation in backend
- Check inventory updates
- Verify shipping calculation

**Success Criteria:**
- Checkout flow completes successfully
- Order created in order-service
- Payment processed by payment-service
- Inventory updated by inventory-service
- Shipping created in shipping-service

---

### Task 3.5: Test Sell Page Flow
**Status:** ❌ Not Started
**Priority:** HIGH
**Estimated Time:** 30 minutes

**Steps:**
1. Navigate to sell page
2. Fill product information
3. Select category
4. Upload images
5. Submit product
6. Verify product creation
7. Check product appears in catalog

**Test Cases:**
- Create product with all fields
- Validate required fields
- Test category selection
- Test image upload
- Verify product appears in catalog

**Success Criteria:**
- Product creation works
- Product appears in inventory
- Categories load correctly
- Images upload successfully

---

### Task 3.6: Test Payment Page Flow
**Status:** ❌ Not Started
**Priority:** MEDIUM
**Estimated Time:** 30 minutes

**Steps:**
1. Navigate to payment page
2. View payment history
3. Check saved payment methods
4. Test adding new payment method
5. Test setting default method
6. Test deleting method

**Test Cases:**
- Load payment history
- Display saved methods
- Add new method
- Set default method
- Delete method
- View order details from payment

**Success Criteria:**
- Payment history loads from backend
- Saved methods display correctly
- Method management works

---

### Task 3.7: Test Admin Page Flow
**Status:** ❌ Not Started
**Priority:** MEDIUM
**Estimated Time:** 30 minutes

**Steps:**
1. Navigate to admin page
2. Verify admin access control
3. Test product management
4. Test order management
5. Test user management

**Test Cases:**
- Access with admin role
- Access denied without admin role
- View all products
- Edit product
- Delete product
- View all orders
- Update order status

**Success Criteria:**
- Admin-only access enforced
- Product management works
- Order management works

---

## Phase 4: Backend Optimization (Priority 3)
**Timeline:** 1-2 hours
**Goal:** Improve backend performance and efficiency

### Task 4.1: Add Database Connection Pooling
**Status:** ❌ Not Started
**Priority:** MEDIUM
**Estimated Time:** 30 minutes

**Steps:**
1. Configure HikariCP for each service
2. Set optimal pool sizes
3. Configure connection timeouts
4. Test connection pool performance

**Configuration Template:**
```yaml
spring:
  datasource:
    hikari:
      maximum-pool-size: 10
      minimum-idle: 5
      connection-timeout: 30000
      idle-timeout: 600000
      max-lifetime: 1800000
```

**Success Criteria:**
- Connection pooling configured
- No connection timeouts
- Improved response times

---

### Task 4.2: Add Caching Layer
**Status:** ❌ Not Started
**Priority:** MEDIUM
**Estimated Time:** 45 minutes

**Steps:**
1. Add Spring Cache dependency
2. Configure cache provider (Redis or Caffeine)
3. Add @Cacheable annotations to read operations
4. Test cache performance

**Implementation:**
```java
@Cacheable("products")
public List<Product> getAllProducts() {
    // implementation
}

@CacheEvict(value = "products", allEntries = true)
public void addProduct(Product product) {
    // implementation
}
```

**Success Criteria:**
- Cache configured and working
- Improved response times for cached data
- Cache invalidation works correctly

---

### Task 4.3: Add API Rate Limiting
**Status:** ❌ Not Started
**Priority:** LOW
**Estimated Time:** 30 minutes

**Steps:**
1. Add Spring Boot Starter for rate limiting
2. Configure rate limits per endpoint
3. Test rate limiting
4. Add rate limit headers to responses

**Success Criteria:**
- Rate limiting implemented
- API protected from abuse
- Rate limit headers present

---

### Task 4.4: Optimize Database Queries
**Status:** ❌ Not Started
**Priority:** MEDIUM
**Estimated Time:** 30 minutes

**Steps:**
1. Review entity relationships
2. Add appropriate JPA fetch strategies
3. Add database indexes where needed
4. Test query performance

**Success Criteria:**
- No N+1 query problems
- Appropriate use of lazy/eager loading
- Database indexes created

---

## Phase 5: Documentation and Monitoring (Priority 3)
**Timeline:** 1 hour
**Goal:** Complete documentation and set up monitoring

### Task 5.1: Create API Documentation
**Status:** ❌ Not Started
**Priority:** MEDIUM
**Estimated Time:** 30 minutes

**Steps:**
1. Add SpringDoc OpenAPI dependency
2. Configure Swagger UI
3. Add API documentation annotations
4. Test Swagger UI access

**Success Criteria:**
- Swagger UI accessible at /swagger-ui.html
- All endpoints documented
- Request/response examples provided

---

### Task 5.2: Add Health Check Improvements
**Status:** ❌ Not Started
**Priority:** MEDIUM
**Estimated Time:** 15 minutes

**Steps:**
1. Add custom health indicators
2. Include database connectivity checks
3. Include RabbitMQ connectivity checks
4. Test health endpoints

**Success Criteria:**
- Health checks include all dependencies
- Accurate health status reported
- Health checks respond quickly

---

### Task 5.3: Add Logging Configuration
**Status:** ❌ Not Started
**Priority:** MEDIUM
**Estimated Time:** 15 minutes

**Steps:**
1. Configure log levels for each service
2. Add structured logging
3. Configure log aggregation (optional)
4. Test logging output

**Success Criteria:**
- Appropriate log levels set
- Logs are structured and searchable
- Important events logged

---

## Phase 6: Final Testing and Deployment (Priority 1)
**Timeline:** 1 hour
**Goal:** Final verification and deployment preparation

### Task 6.1: End-to-End System Test
**Status:** ❌ Not Started
**Priority:** CRITICAL
**Estimated Time:** 30 minutes

**Steps:**
1. Test complete user journey
2. Test all error scenarios
3. Test concurrent users
4. Verify data consistency

**Test Scenarios:**
- Complete purchase flow
- User registration and login
- Product listing and search
- Order management
- Error handling

**Success Criteria:**
- All user flows work end-to-end
- System handles errors gracefully
- Data remains consistent

---

### Task 6.2: Performance Testing
**Status:** ❌ Not Started
**Priority:** MEDIUM
**Estimated Time:** 15 minutes

**Steps:**
1. Load test critical endpoints
2. Measure response times
3. Identify bottlenecks
4. Optimize if needed

**Success Criteria:**
- Response times under 500ms for most endpoints
- System handles 100 concurrent users
- No memory leaks

---

### Task 6.3: Security Review
**Status:** ❌ Not Started
**Priority:** HIGH
**Estimated Time:** 15 minutes

**Steps:**
1. Review authentication implementation
2. Check for SQL injection vulnerabilities
3. Verify input validation
4. Check CORS configuration

**Success Criteria:**
- Authentication properly implemented
- No SQL injection vulnerabilities
- Input validation on all endpoints
- CORS properly configured

---

## Execution Order

### Immediate (Start Now)
1. Task 1.1: Fix Auth Service Database Configuration
2. Task 1.2: Fix Missing Use Case Beans - Order Service
3. Task 1.3: Fix Missing Use Case Beans - Payment Service

### After Services Are Healthy
4. Task 1.4: Fix Inventory Service Use Cases
5. Task 1.5: Fix Shipping Service Use Cases
6. Task 1.6: Fix Notification Service
7. Task 2.1: Create Database Schemas
8. Task 2.2: Update Service Configurations for Schema Usage

### After Database Is Ready
9. Task 2.3: Verify Table Creation
10. Task 3.1: Test Authentication Flow
11. Task 3.2: Test Product Catalog Flow
12. Task 3.3: Test Shopping Cart Flow
13. Task 3.4: Test Checkout Flow
14. Task 3.5: Test Sell Page Flow
15. Task 3.6: Test Payment Page Flow
16. Task 3.7: Test Admin Page Flow

### Optimization Phase
17. Task 4.1: Add Database Connection Pooling
18. Task 4.2: Add Caching Layer
19. Task 4.3: Add API Rate Limiting
20. Task 4.4: Optimize Database Queries

### Documentation Phase
21. Task 5.1: Create API Documentation
22. Task 5.2: Add Health Check Improvements
23. Task 5.3: Add Logging Configuration

### Final Phase
24. Task 6.1: End-to-End System Test
25. Task 6.2: Performance Testing
26. Task 6.3: Security Review

---

## Risk Mitigation

### High Risk Items
1. **Database Schema Migration** - Risk of data loss
   - Mitigation: Backup database before changes
   - Test on staging environment first

2. **Service Configuration Changes** - Risk of breaking working services
   - Mitigation: Make one change at a time
   - Test each change before proceeding

3. **Bean Configuration Fixes** - Risk of introducing new dependency issues
   - Mitigation: Check all dependencies carefully
   - Test service startup after each fix

### Medium Risk Items
1. **Performance Optimization** - Risk of performance regression
   - Mitigation: Benchmark before and after
   - Roll back if performance degrades

2. **Caching Implementation** - Risk of stale data
   - Mitigation: Implement proper cache invalidation
   - Monitor cache hit rates

---

## Success Criteria

### Phase 1 Success
- All 6 backend services healthy
- No startup errors in logs
- Health endpoints return 200 OK

### Phase 2 Success
- All 6 schemas created
- All tables created in correct schemas
- No schema-related errors

### Phase 3 Success
- All user flows tested and working
- No critical bugs found
- Frontend-backend integration complete

### Phase 4 Success
- Response times improved by 30%
- No performance regressions
- System handles 100+ concurrent users

### Phase 5 Success
- API documentation complete
- Monitoring configured
- Logging properly set up

### Phase 6 Success
- System passes all tests
- Performance meets requirements
- Security review passed

---

## Next Steps

**Immediate Action:** Start with Task 1.1 - Fix Auth Service Database Configuration

**Command to Start:**
```bash
cd Backend
docker-compose logs auth-service --tail 100
```

This will give us the detailed logs needed to understand the exact configuration issue with the auth service.
