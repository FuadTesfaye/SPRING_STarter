# Toobuy E-commerce Platform

A modern e-commerce platform built with React, Spring Boot, and Supabase authentication.

## 🚀 Features

- **Modern UI**: eBay-inspired design with circular account elements
- **Authentication**: Supabase-based authentication system
- **Microservices**: Spring Boot microservices architecture
- **Real-time Events**: RabbitMQ event-driven architecture
- **Responsive Design**: Mobile-first responsive design

## 🛠️ Tech Stack

### Frontend
- React 18
- TypeScript
- Tailwind CSS
- Zustand (State Management)
- Supabase Auth
- React Router
- Lucide Icons

### Backend
- Spring Boot
- Java 17+
- RabbitMQ
- Microservices Architecture
- Testcontainers (Testing)

### Authentication
- Supabase Authentication
- JWT Tokens

## 📦 Installation

### Prerequisites
- Node.js 18+
- Java 17+
- Docker & Docker Compose
- Supabase Account

### Setup

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd jjhu
   ```

2. **Setup Supabase**
   - Create a new Supabase project
   - Copy the project URL and anon key
   - Create `.env` file in the root:
     ```env
     VITE_SUPABASE_URL=https://your-project.supabase.co
     VITE_SUPABASE_ANON_KEY=your-anon-key
     ```

3. **Install Frontend Dependencies**
   ```bash
   cd client
   npm install
   ```

4. **Setup Database Schema**
   - Create the following tables in your Supabase project:
     - `user_profiles`
     - `products`
     - `orders`
     - `categories`
     - etc. (See Database/schema.sql for reference)

5. **Start the Application**
   ```bash
   # Start frontend
   cd client
   npm run dev

   # Start backend services (if using local microservices)
   cd Backend
   # Run each service individually or use docker-compose
   ```

## 🏗️ Project Structure

```
jjhu/
├── client/                 # React frontend
│   ├── src/
│   │   ├── components/     # Reusable components
│   │   ├── pages/         # Page components
│   │   ├── store/         # Zustand stores
│   │   ├── lib/           # Utilities (Supabase config)
│   │   └── types/         # TypeScript types
│   └── package.json
├── Backend/               # Spring Boot microservices
│   ├── auth-service/      # Authentication service
│   ├── order-service/     # Order management
│   ├── payment-service/   # Payment processing
│   ├── inventory-service/ # Inventory management
│   ├── shipping-service/  # Shipping management
│   └── notification-service/ # Notifications
├── Database/              # Database schemas and migrations
└── README.md
```

## 🔐 Authentication

The platform uses Supabase for authentication:

- **Sign Up**: Email/password registration
- **Sign In**: Email/password authentication
- **Session Management**: Automatic token handling
- **User Profiles**: Extended user information storage

## 🎨 UI Features

- **Circular Account Avatars**: eBay-style circular profile pictures
- **Dropdown Menus**: Hover-based account dropdowns
- **Responsive Design**: Mobile and desktop optimized
- **Dark Mode**: Built-in dark theme support
- **Modern Styling**: Gradient backgrounds and smooth transitions

## 🔄 Migration from PostgreSQL

This project has been migrated from local PostgreSQL to Supabase:

1. ✅ Removed PostgreSQL containers from integration tests
2. ✅ Added Supabase client library
3. ✅ Updated authentication store
4. ✅ Modified UI components for circular account elements
5. ✅ Updated login/register pages with eBay-like styling

## 🧪 Testing

```bash
# Frontend tests
cd client
npm test

# Backend integration tests
cd Backend/integration-tests
mvn test
```

## 📝 Environment Variables

Create a `.env` file in the root directory:

```env
# Supabase Configuration
VITE_SUPABASE_URL=https://your-project.supabase.co
VITE_SUPABASE_ANON_KEY=your-anon-key

# Backend API URLs (if using local services)
VITE_API_BASE_URL=http://localhost:8081
VITE_ORDER_SERVICE_URL=http://localhost:8082
VITE_PAYMENT_SERVICE_URL=http://localhost:8083
VITE_INVENTORY_SERVICE_URL=http://localhost:8084
VITE_SHIPPING_SERVICE_URL=http://localhost:8085
VITE_NOTIFICATION_SERVICE_URL=http://localhost:8086
```

## 🚀 Deployment

### Frontend (Vercel/Netlify)
1. Build the frontend: `npm run build`
2. Deploy to your preferred platform
3. Set environment variables in the deployment platform

### Backend (Docker/Cloud)
1. Build Docker images for each microservice
2. Deploy to your preferred cloud platform
3. Configure environment variables and database connections

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License.

## 🆘 Support

For support and questions:
- Create an issue in the repository
- Check the documentation
- Review the setup instructions
