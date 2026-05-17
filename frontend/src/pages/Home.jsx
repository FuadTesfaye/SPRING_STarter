import { ShoppingBag, CreditCard, Package, Truck } from 'lucide-react'

export default function Home() {
  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
      <div className="text-center mb-12">
        <h1 className="text-4xl font-bold text-gray-900 mb-4">
          Welcome to Campus Commerce System
        </h1>
        <p className="text-xl text-gray-600">
          Your one-stop shop for campus products and services
        </p>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
        <div className="bg-white rounded-lg shadow-md p-6 hover:shadow-lg transition">
          <ShoppingBag className="h-12 w-12 text-blue-600 mb-4" />
          <h3 className="text-lg font-semibold mb-2">Browse Products</h3>
          <p className="text-gray-600">Explore our wide range of campus products</p>
        </div>

        <div className="bg-white rounded-lg shadow-md p-6 hover:shadow-lg transition">
          <CreditCard className="h-12 w-12 text-green-600 mb-4" />
          <h3 className="text-lg font-semibold mb-2">Easy Payments</h3>
          <p className="text-gray-600">Secure and fast payment processing</p>
        </div>

        <div className="bg-white rounded-lg shadow-md p-6 hover:shadow-lg transition">
          <Package className="h-12 w-12 text-purple-600 mb-4" />
          <h3 className="text-lg font-semibold mb-2">Real-time Inventory</h3>
          <p className="text-gray-600">Live stock availability updates</p>
        </div>

        <div className="bg-white rounded-lg shadow-md p-6 hover:shadow-lg transition">
          <Truck className="h-12 w-12 text-orange-600 mb-4" />
          <h3 className="text-lg font-semibold mb-2">Fast Shipping</h3>
          <p className="text-gray-600">Track your orders in real-time</p>
        </div>
      </div>

      <div className="mt-12 bg-blue-600 rounded-lg shadow-md p-8 text-white text-center">
        <h2 className="text-2xl font-bold mb-4">Get Started Today</h2>
        <p className="mb-6">Register now to start shopping</p>
        <button className="bg-white text-blue-600 px-6 py-3 rounded-lg font-semibold hover:bg-blue-50 transition">
          Create Account
        </button>
      </div>
    </div>
  )
}
