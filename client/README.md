# Ecommerce Microservices Dashboard

A modern, professional dashboard for monitoring and controlling a distributed Ecommerce Event-Driven Microservices System built with Spring Boot and RabbitMQ.

## 🚀 Features

### Authentication
- **Login Page**: Secure JWT-based authentication
- **Register Page**: User registration with validation
- **Protected Routes**: Route guards for authenticated access
- **Session Management**: Automatic token refresh and logout

### Dashboard
- **Real-time Statistics**: Live metrics for all microservices
- **Interactive Charts**: Order statistics, payment analytics, shipment trends
- **Glassmorphism Design**: Modern, enterprise-grade UI
- **Responsive Layout**: Works seamlessly on all devices

### Order Management
- **Create Orders**: Visual form with real-time validation
- **Event Pipeline**: Animated workflow visualization
- **Order History**: Comprehensive order tracking
- **Search & Filter**: Advanced filtering capabilities

### Event Monitoring
- **Live Event Feed**: Real-time event streaming
- **Event Filtering**: Filter by event type and service
- **Auto-refresh**: Configurable update intervals
- **Event Details**: Complete event information display

### System Health
- **Service Monitoring**: Health checks for all microservices
- **Response Time Tracking**: Performance metrics
- **Visual Status Indicators**: Color-coded health status
- **Auto-refresh**: Real-time monitoring

### Architecture Visualization
- **Interactive Diagram**: Animated service connections
- **Workflow Animation**: Step-by-step process visualization
- **Technology Stack**: Complete tech overview
- **Event Flow**: Visual representation of message flow

## 🛠️ Technology Stack

### Frontend
- **React 18**: Modern UI framework
- **TypeScript**: Type-safe development
- **Vite**: Fast build tool and dev server
- **TailwindCSS**: Utility-first CSS framework
- **React Router**: Client-side routing
- **Zustand**: Lightweight state management
- **Axios**: HTTP client for API calls
- **Recharts**: Data visualization library
- **Lucide React**: Modern icon library

### Backend Integration
- **Spring Boot**: Microservices framework
- **RabbitMQ**: Message broker for event-driven architecture
- **JWT**: Authentication tokens
- **REST APIs**: Standardized service endpoints

## 📁 Project Structure

```
src/
├── components/          # Reusable UI components
│   └── Layout.tsx      # Main application layout
├── pages/              # Page components
│   ├── LoginPage.tsx
│   ├── RegisterPage.tsx
│   ├── DashboardPage.tsx
│   ├── CreateOrderPage.tsx
│   ├── EventMonitorPage.tsx
│   ├── OrdersPage.tsx
│   ├── SystemHealthPage.tsx
│   └── ArchitecturePage.tsx
├── services/           # API service layers
│   ├── api.ts         # Base API configuration
│   ├── authService.ts  # Authentication service
│   ├── orderService.ts # Order management
│   ├── eventService.ts # Event handling
│   └── healthService.ts# Health monitoring
├── store/              # State management
│   ├── authStore.ts   # Authentication state
│   ├── dashboardStore.ts
│   ├── orderStore.ts
│   ├── eventStore.ts
│   ├── healthStore.ts
│   └── index.ts       # Store exports
├── types/              # TypeScript type definitions
│   └── index.ts       # All type definitions
├── hooks/              # Custom React hooks
├── utils/              # Utility functions
└── App.tsx            # Main application component
```

## 🚀 Getting Started

### Prerequisites
- Node.js 18+ 
- npm or yarn
- Backend microservices running on specified ports

### Installation

1. **Clone repository**
   ```bash
   git clone <repository-url>
   cd client
   ```

2. **Install dependencies**
   ```bash
   npm install
   ```

3. **Start development server**
   ```bash
   npm run dev
   ```

4. **Access application**
   ```
   http://localhost:5173
   ```

### Environment Setup

Ensure backend services are running on:
- Auth Service: http://localhost:8081
- Order Service: http://localhost:8082
- Payment Service: http://localhost:8083
- Inventory Service: http://localhost:8084
- Shipping Service: http://localhost:8085
- Notification Service: http://localhost:8086
- RabbitMQ Management: http://localhost:15672

## 📊 Available Scripts

```bash
# Development
npm run dev          # Start development server
npm run build        # Build for production
npm run preview      # Preview production build

# Code Quality
npm run lint         # Run ESLint
```

## 🔧 Configuration

### Environment Variables
Create a `.env` file in root directory:

```env
VITE_API_BASE_URL=http://localhost:8081
VITE_RABBITMQ_URL=http://localhost:15672
```

### TailwindCSS Configuration
The project uses a custom TailwindCSS configuration with:
- Dark mode support
- Custom color schemes
- Glassmorphism utilities
- Custom animations

## 🎨 Design Features

### UI/UX
- **Glassmorphism**: Modern glass-like effects
- **Dark Mode**: Built-in dark theme support
- **Responsive Design**: Mobile-first approach
- **Micro-interactions**: Smooth transitions and animations
- **Loading States**: Professional loading indicators
- **Error Handling**: User-friendly error messages

### Accessibility
- Semantic HTML structure
- Keyboard navigation support
- Screen reader compatibility
- High contrast ratios
- Focus indicators

## 🔄 API Integration

### Authentication Flow
1. User logs in via Auth Service
2. JWT token received and stored
3. Token attached to all subsequent requests
4. Automatic logout on token expiration

### Event-Driven Architecture
- Real-time event streaming via Server-Sent Events
- Event filtering and search capabilities
- Automatic reconnection handling
- Event history persistence

### Health Monitoring
- Periodic health checks every 10 seconds
- Response time tracking
- Service dependency visualization
- Historical health data

## 🚀 Deployment

### Production Build
```bash
npm run build
```

### Docker Deployment
```dockerfile
FROM node:18-alpine
WORKDIR /app
COPY package*.json ./
RUN npm ci --only=production
COPY dist ./dist
EXPOSE 5173
CMD ["npm", "run", "preview"]
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## 📝 License

This project is licensed under the MIT License.

## 🆘 Troubleshooting

### Common Issues

**CORS Errors**: Ensure backend services have CORS configured
**Connection Refused**: Verify all backend services are running
**Authentication Issues**: Check JWT token configuration
**Event Stream Issues**: Verify RabbitMQ connection

### Development Tips

- Use browser DevTools for debugging
- Check Network tab for API calls
- Monitor Console for event stream messages
- Use React DevTools for state inspection

## 📞 Support

For support and questions:
- Check the documentation
- Review the troubleshooting section
- Open an issue on GitHub

---

**Built with ❤️ for distributed systems monitoring**
