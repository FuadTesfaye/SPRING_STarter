import { ShoppingCart } from 'lucide-react'

export default function Orders() {
  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
      <div className="flex items-center mb-6">
        <ShoppingCart className="h-8 w-8 text-blue-600 mr-2" />
        <h1 className="text-3xl font-bold text-gray-900">Your Orders</h1>
      </div>

      <div className="bg-white rounded-lg shadow-md p-8">
        <p className="text-gray-600 text-center py-12">
          No orders yet. Start shopping to see your orders here.
        </p>
      </div>
    </div>
  )
}
