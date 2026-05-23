# 🚀 Quick Start Guide

## Current Status
✅ **Node.js Backend**: Running on `http://localhost:8081`  
✅ **Frontend**: Running on `http://localhost:5174`  
❌ **Docker Desktop**: Unable to start  

## Option 1: Use Node.js Backend (Recommended - Working Now!)

The Node.js backend is already running and provides all necessary APIs:

### Start Backend (if stopped)
```bash
# Navigate to Backend directory
cd c:\Users\Philemon Daniel\Desktop\jjhu\Backend

# Run the startup script
start-backend.bat

# Or manually
cd nodejs-backend
npm start
```

### Frontend Access
- **URL**: `http://localhost:5174`
- **Status**: ✅ Uses real backend data
- **Features**: Login, Products, Cart, Checkout

## Option 2: Full Docker Microservices (Requires Docker Desktop)

### Prerequisites
1. Start Docker Desktop
2. Ensure sufficient RAM (8GB+ recommended)

### Start Services
```bash
cd c:\Users\Philemon Daniel\Desktop\jjhu\Backend
docker-compose up -d
```

### Services
- **Auth**: Port 8081
- **Order**: Port 8082  
- **Payment**: Port 8083
- **Inventory**: Port 8084
- **Shipping**: Port 8085
- **Notification**: Port 8086

## API Testing

### Test Current Backend
```bash
# Health check
curl http://localhost:8081/health

# Get products
curl http://localhost:8081/products

# Test login
curl -X POST http://localhost:8081/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"test@example.com","password":"password"}'
```

## Frontend Integration

The frontend automatically:
1. **Tries real backend first** (Node.js or Docker)
2. **Falls back to mock data** if backend unavailable
3. **Provides seamless experience** regardless of backend status

## Troubleshooting

### Docker Issues
If Docker Desktop won't start:
1. Restart Docker Desktop
2. Check system resources
3. Use Node.js backend (recommended for development)

### Port Conflicts
If port 8081 is busy:
1. Stop conflicting services
2. Modify port in `nodejs-backend/server.js`

### Frontend Issues
If frontend shows mock data:
1. Check backend is running: `curl http://localhost:8081/health`
2. Verify CORS is enabled
3. Check browser console for errors

## Development Recommendation

**Use Node.js backend for development**:
- ✅ Fast startup
- ✅ Simple to debug
- ✅ All required endpoints
- ✅ No Docker dependency

**Use Docker microservices for production**:
- ✅ Full event-driven architecture
- ✅ Separate databases
- ✅ Scalable and resilient
- ✅ Production-ready

---

**Current Setup**: Node.js backend + React frontend = ✅ Working with real data!
