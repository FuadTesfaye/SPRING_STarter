import { Link } from 'react-router-dom'
import { ShoppingCart, User, LogIn } from 'lucide-react'

export default function Navbar() {
  return (
    <nav className="bg-blue-600 text-white shadow-lg">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex items-center justify-between h-16">
          <div className="flex items-center">
            <ShoppingCart className="h-8 w-8 mr-2" />
            <Link to="/" className="text-xl font-bold">
              Campus Commerce
            </Link>
          </div>
          <div className="flex space-x-4">
            <Link to="/" className="hover:text-blue-200 transition">
              Home
            </Link>
            <Link to="/orders" className="hover:text-blue-200 transition">
              Orders
            </Link>
            <Link to="/login" className="flex items-center hover:text-blue-200 transition">
              <LogIn className="h-5 w-5 mr-1" />
              Login
            </Link>
            <Link to="/register" className="flex items-center hover:text-blue-200 transition">
              <User className="h-5 w-5 mr-1" />
              Register
            </Link>
          </div>
        </div>
      </div>
    </nav>
  )
}
