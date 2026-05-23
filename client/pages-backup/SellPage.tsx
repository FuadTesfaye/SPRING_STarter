import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { 
  Plus, 
  CheckCircle, 
  AlertCircle,
  Package,
  DollarSign,
  FileText,
  Layers,
  Box
} from 'lucide-react';
import { useProductStore } from '../store/productStore';
import { useAuthStore } from '../store';
import ImageUpload from '../components/ImageUpload';
import CategoryFormFields from '../components/CategoryFormFields';
import type { CategoryAttributes } from '../types/product';

interface ProductFormData {
  name: string;
  description: string;
  price: string;
  comparePrice: string;
  category: string;
  images: string[];
  stock: string;
  featured: boolean;
  categoryAttributes?: Partial<CategoryAttributes>;
  // eBay-like shipping information
  shipping: {
    freeShipping: boolean;
    shippingCost: string;
    handlingTime: string;
    returnsAccepted: boolean;
    shippingFrom: string;
  };
}

interface FormErrors {
  name?: string;
  description?: string;
  price?: string;
  comparePrice?: string;
  category?: string;
  images?: string;
  stock?: string;
  featured?: string;
  categoryAttributes?: Partial<CategoryAttributes>;
}

export default function SellPage() {
  const navigate = useNavigate();
  const { addProduct, isLoading, error, clearError, fetchCategories, categories, fetchProducts, products } = useProductStore();
  const { isAuthenticated } = useAuthStore();
  
  const [formData, setFormData] = useState<ProductFormData>({
    name: '',
    description: '',
    price: '',
    comparePrice: '',
    category: '',
    images: [],
    stock: '',
    featured: false,
    categoryAttributes: {},
    shipping: {
      freeShipping: true,
      shippingCost: '',
      handlingTime: '3 business days',
      returnsAccepted: true,
      shippingFrom: ''
    }
  });
  
  const [formErrors, setFormErrors] = useState<FormErrors>({});
  const [submitSuccess, setSubmitSuccess] = useState(false);

  useEffect(() => {
    fetchCategories();
    fetchProducts();
    if (!isAuthenticated) {
      navigate('/login');
    }
  }, [isAuthenticated, navigate, fetchCategories, fetchProducts]);

  const validateForm = (): boolean => {
    const errors: FormErrors = {};
    console.log('=== SELL PAGE VALIDATION ===');
    console.log('Form data during validation:', formData);
    console.log('Images array:', formData.images);
    console.log('Images length:', formData.images.length);
    console.log('Current page: SellPage.tsx');

    if (!formData.name.trim()) {
      errors.name = 'Product name is required';
    }

    if (!formData.description.trim()) {
      errors.description = 'Product description is required';
    } else if (formData.description.length < 10) {
      errors.description = 'Description must be at least 10 characters';
    }

    if (!formData.price) {
      errors.price = 'Price is required';
    } else if (isNaN(Number(formData.price)) || Number(formData.price) <= 0) {
      errors.price = 'Price must be a positive number';
    }

    if (formData.comparePrice && (isNaN(Number(formData.comparePrice)) || Number(formData.comparePrice) <= 0)) {
      errors.comparePrice = 'Compare price must be a positive number';
    }

    if (!formData.category) {
      errors.category = 'Category is required';
    }

    // Temporarily bypass image validation for testing
    // if (formData.images.length === 0) {
    //   errors.images = 'At least one product image is required';
    // }

    if (!formData.stock) {
      errors.stock = 'Stock quantity is required';
    } else if (isNaN(Number(formData.stock)) || Number(formData.stock) < 0) {
      errors.stock = 'Stock must be a non-negative number';
    }

    setFormErrors(errors);
    return Object.keys(errors).length === 0;
  };

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement>) => {
    const { name, value, type } = e.target;
    
    if (type === 'checkbox') {
      setFormData(prev => ({
        ...prev,
        [name]: (e.target as HTMLInputElement).checked
      }));
    } else {
      setFormData(prev => ({
        ...prev,
        [name]: value
      }));
    }

    // Clear error for this field
    if (formErrors[name as keyof FormErrors]) {
      setFormErrors(prev => ({
        ...prev,
        [name]: undefined
      }));
    }
  };

  const handleImagesSelect = (images: string[]) => {
    setFormData(prev => ({ ...prev, images }));

    if (formErrors.images) {
      setFormErrors(prev => ({ ...prev, images: undefined }));
    }
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    clearError();

    if (!validateForm()) {
      return;
    }

    try {
      const productData = {
        name: formData.name.trim(),
        description: formData.description.trim(),
        price: Number(formData.price),
        comparePrice: formData.comparePrice ? Number(formData.comparePrice) : undefined,
        category: formData.category,
        images: formData.images,
        stock: Number(formData.stock),
        rating: 0,
        reviews: 0,
        featured: formData.featured,
        categoryAttributes: formData.categoryAttributes
      };

      console.log('Adding product:', productData);
      const newProduct = await addProduct(productData);
      console.log('Product added successfully:', newProduct);
      setSubmitSuccess(true);
      
      // eBay-like flow: Show success and redirect to products page
      setTimeout(() => {
        navigate('/products'); // Redirect to products page to see the new listing
      }, 1500);

    } catch (err) {
      console.error('Failed to add product:', err);
    }
  };

  if (!isAuthenticated) {
    return null;
  }

  return (
    <div className="min-h-screen bg-gray-50 py-8">
      <div className="max-w-4xl mx-auto px-4">
        <div className="bg-white rounded-lg shadow-sm border border-gray-200">
          {/* Header */}
          <div className="border-b border-gray-200 p-6">
            <div className="flex items-center gap-3">
              <div className="w-10 h-10 bg-blue-100 rounded-lg flex items-center justify-center">
                <Plus className="w-5 h-5 text-blue-600" />
              </div>
              <div>
                <h1 className="text-2xl font-bold text-gray-900">Sell a Product</h1>
                <p className="text-gray-600">Add your product to the marketplace</p>
              </div>
            </div>
          </div>

          {/* eBay-like Success Message */}
          {submitSuccess && (
            <div className="m-6 p-6 bg-green-50 border border-green-200 rounded-lg">
              <div className="flex items-center gap-3 mb-3">
                <CheckCircle className="w-6 h-6 text-green-500" />
                <div>
                  <h3 className="text-green-800 font-semibold">Listing Created Successfully!</h3>
                  <p className="text-green-600 text-sm">Your product is now live and visible to buyers</p>
                </div>
              </div>
              <div className="bg-green-100 rounded p-3 text-sm text-green-700">
                <p className="font-medium mb-1">What happens next:</p>
                <ul className="text-xs space-y-1 ml-4">
                  <li>• Redirecting to your product listing...</li>
                  <li>• Buyers can now view and purchase your item</li>
                  <li>• You'll receive notifications for new orders</li>
                </ul>
              </div>
            </div>
          )}

          {/* Error Message */}
          {error && (
            <div className="m-6 p-4 bg-red-50 border border-red-200 rounded-lg flex items-center gap-3">
              <AlertCircle className="w-5 h-5 text-red-500" />
              <span className="text-red-700">{error}</span>
            </div>
          )}

          {/* Form */}
          <form onSubmit={handleSubmit} className="p-6 space-y-6">
            {/* Product Name - Always Visible */}
            <div>
              <label className="flex items-center gap-2 text-sm font-medium text-gray-700 mb-2">
                <Package className="w-4 h-4" />
                Product Name *
              </label>
              <input
                type="text"
                name="name"
                value={formData.name}
                onChange={handleInputChange}
                className={`w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 ${
                  formErrors.name ? 'border-red-500' : 'border-gray-300'
                }`}
                placeholder="Enter product name"
                maxLength={100}
              />
              {formErrors.name && (
                <p className="mt-1 text-sm text-red-600">{formErrors.name}</p>
              )}
            </div>

            {/* Show category and other fields only if product name is entered */}
            {formData.name.trim() && (
              <>
                {/* Description */}
                <div>
                  <label className="flex items-center gap-2 text-sm font-medium text-gray-700 mb-2">
                    <FileText className="w-4 h-4" />
                    Description *
                  </label>
                  <textarea
                    name="description"
                    value={formData.description}
                    onChange={handleInputChange}
                    rows={4}
                    className={`w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 ${
                      formErrors.description ? 'border-red-500' : 'border-gray-300'
                    }`}
                    placeholder="Describe your product in detail"
                    maxLength={1000}
                  />
                  {formErrors.description && (
                    <p className="mt-1 text-sm text-red-600">{formErrors.description}</p>
                  )}
                  <p className="mt-1 text-sm text-gray-500">{formData.description.length}/1000 characters</p>
                </div>

                <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                  {/* Price */}
                  <div>
                    <label className="flex items-center gap-2 text-sm font-medium text-gray-700 mb-2">
                      <DollarSign className="w-4 h-4" />
                      Price ($) *
                    </label>
                    <input
                      type="number"
                      name="price"
                      value={formData.price}
                      onChange={handleInputChange}
                      step="0.01"
                      min="0"
                      className={`w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 ${
                        formErrors.price ? 'border-red-500' : 'border-gray-300'
                      }`}
                      placeholder="0.00"
                    />
                    {formErrors.price && (
                      <p className="mt-1 text-sm text-red-600">{formErrors.price}</p>
                    )}
                  </div>

                  {/* Compare Price */}
                  <div>
                    <label className="flex items-center gap-2 text-sm font-medium text-gray-700 mb-2">
                      <DollarSign className="w-4 h-4" />
                      Compare Price ($)
                    </label>
                    <input
                      type="number"
                      name="comparePrice"
                      value={formData.comparePrice}
                      onChange={handleInputChange}
                      step="0.01"
                      min="0"
                      className={`w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 ${
                        formErrors.comparePrice ? 'border-red-500' : 'border-gray-300'
                      }`}
                      placeholder="0.00"
                    />
                    {formErrors.comparePrice && (
                      <p className="mt-1 text-sm text-red-600">{formErrors.comparePrice}</p>
                    )}
                    <p className="mt-1 text-sm text-gray-500">Optional: Show original price for discount display</p>
                  </div>
                </div>

                <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                  {/* Category */}
                  <div>
                    <label className="flex items-center gap-2 text-sm font-medium text-gray-700 mb-2">
                      <Layers className="w-4 h-4" />
                      Category *
                    </label>
                    <select
                      name="category"
                      value={formData.category}
                      onChange={handleInputChange}
                      className={`w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 ${
                        formErrors.category ? 'border-red-500' : 'border-gray-300'
                      }`}
                    >
                      <option value="">Select a category</option>
                      {categories.map(category => (
                        <option key={category} value={category}>{category}</option>
                      ))}
                    </select>
                    {formErrors.category && (
                      <p className="mt-1 text-sm text-red-600">{formErrors.category}</p>
                    )}
                  </div>

                  {/* Stock */}
                  <div>
                    <label className="flex items-center gap-2 text-sm font-medium text-gray-700 mb-2">
                      <Box className="w-4 h-4" />
                      Stock Quantity *
                    </label>
                    <input
                      type="number"
                      name="stock"
                      value={formData.stock}
                      onChange={handleInputChange}
                      min="0"
                      className={`w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 ${
                        formErrors.stock ? 'border-red-500' : 'border-gray-300'
                      }`}
                      placeholder="0"
                    />
                    {formErrors.stock && (
                      <p className="mt-1 text-sm text-red-600">{formErrors.stock}</p>
                    )}
                  </div>
                </div>

                {/* Show category-specific fields only after category is selected */}
                {formData.category && (
                  <>
                    {/* Image Upload */}
                    <div>
                      <ImageUpload
                        onImagesSelect={handleImagesSelect}
                        currentImages={formData.images}
                        label="Product Images"
                      />
                      {formErrors.images && (
                        <p className="mt-1 text-sm text-red-600">{formErrors.images}</p>
                      )}
                    </div>

                    {/* Category-Specific Fields */}
                    <CategoryFormFields
                      category={formData.category}
                      value={formData.categoryAttributes}
                      onChange={(attributes) => setFormData(prev => ({ ...prev, categoryAttributes: attributes }))}
                      errors={formErrors.categoryAttributes}
                    />

                    {/* Featured Checkbox */}
                    <div>
                      <label className="flex items-center gap-2 text-sm font-medium text-gray-700">
                        <input
                          type="checkbox"
                          name="featured"
                          checked={formData.featured}
                          onChange={handleInputChange}
                          className="w-4 h-4 text-blue-600 border-gray-300 rounded focus:ring-blue-500"
                        />
                        Feature this product on the homepage
                      </label>
                      <p className="mt-1 text-sm text-gray-500">Featured products appear in the trending section</p>
                    </div>

                    {/* eBay-like Shipping Information */}
                    <div className="border-t border-gray-200 pt-6">
                      <h3 className="text-lg font-medium text-gray-900 mb-4">Shipping Information</h3>
                      
                      <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                        {/* Free Shipping */}
                        <div>
                          <label className="flex items-center gap-2 text-sm font-medium text-gray-700">
                            <input
                              type="checkbox"
                              name="freeShipping"
                              checked={formData.shipping.freeShipping}
                              onChange={(e) => setFormData(prev => ({
                                ...prev,
                                shipping: { ...prev.shipping, freeShipping: e.target.checked }
                              }))}
                              className="w-4 h-4 text-blue-600 border-gray-300 rounded focus:ring-blue-500"
                            />
                            Free Shipping
                          </label>
                          <p className="mt-1 text-sm text-gray-500">Offer free shipping to attract more buyers</p>
                        </div>

                        {/* Shipping Cost */}
                        <div>
                          <label className="block text-sm font-medium text-gray-700 mb-1">
                            Shipping Cost ($)
                          </label>
                          <input
                            type="number"
                            step="0.01"
                            min="0"
                            value={formData.shipping.shippingCost}
                            onChange={(e) => setFormData(prev => ({
                              ...prev,
                              shipping: { ...prev.shipping, shippingCost: e.target.value }
                            }))}
                            className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
                            placeholder="0.00"
                            disabled={formData.shipping.freeShipping}
                          />
                        </div>

                        {/* Handling Time */}
                        <div>
                          <label className="block text-sm font-medium text-gray-700 mb-1">
                            Handling Time
                          </label>
                          <select
                            value={formData.shipping.handlingTime}
                            onChange={(e) => setFormData(prev => ({
                              ...prev,
                              shipping: { ...prev.shipping, handlingTime: e.target.value }
                            }))}
                            className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
                          >
                            <option value="1 business day">1 business day</option>
                            <option value="2 business days">2 business days</option>
                            <option value="3 business days">3 business days</option>
                            <option value="5 business days">5 business days</option>
                            <option value="1 week">1 week</option>
                          </select>
                        </div>

                        {/* Returns Accepted */}
                        <div>
                          <label className="flex items-center gap-2 text-sm font-medium text-gray-700">
                            <input
                              type="checkbox"
                              name="returnsAccepted"
                              checked={formData.shipping.returnsAccepted}
                              onChange={(e) => setFormData(prev => ({
                                ...prev,
                                shipping: { ...prev.shipping, returnsAccepted: e.target.checked }
                              }))}
                              className="w-4 h-4 text-blue-600 border-gray-300 rounded focus:ring-blue-500"
                            />
                            Returns Accepted
                          </label>
                          <p className="mt-1 text-sm text-gray-500">Build buyer confidence with return policy</p>
                        </div>

                        {/* Ship From */}
                        <div className="md:col-span-2">
                          <label className="block text-sm font-medium text-gray-700 mb-1">
                            Ship From (City, State)
                          </label>
                          <input
                            type="text"
                            value={formData.shipping.shippingFrom}
                            onChange={(e) => setFormData(prev => ({
                              ...prev,
                              shipping: { ...prev.shipping, shippingFrom: e.target.value }
                            }))}
                            className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
                            placeholder="New York, NY"
                          />
                        </div>
                      </div>
                    </div>

                    {/* Submit Button */}
                    <div className="flex gap-4 pt-4">
                      <button
                        type="submit"
                        disabled={isLoading}
                        className="flex-1 bg-blue-600 hover:bg-blue-700 disabled:bg-blue-300 text-white font-medium py-3 px-4 rounded-lg transition-colors flex items-center justify-center gap-2"
                      >
                        {isLoading ? (
                          <>
                            <div className="w-5 h-5 border-2 border-white border-t-transparent rounded-full animate-spin" />
                            Adding Product...
                          </>
                        ) : (
                          <>
                            <Plus className="w-5 h-5" />
                            Add Product
                          </>
                        )}
                      </button>
                      
                      <button
                        type="button"
                        onClick={() => navigate('/products')}
                        className="px-6 py-3 border border-gray-300 text-gray-700 font-medium rounded-lg hover:bg-gray-50 transition-colors"
                      >
                        Cancel
                      </button>
                    </div>
                  </>
                )}
              </>
            )}
          </form>

          {/* Existing Products Section */}
          <div className="border-t border-gray-200 p-6">
            <div className="flex items-center justify-between mb-4">
              <h2 className="text-lg font-semibold text-gray-900">Existing Products ({products.length})</h2>
              <button
                onClick={() => navigate('/products')}
                className="text-blue-600 hover:text-blue-700 text-sm font-medium"
              >
                View All Products →
              </button>
            </div>
            
            {products.length === 0 ? (
              <div className="text-center py-8">
                <Package className="w-12 h-12 text-gray-400 mx-auto mb-3" />
                <p className="text-gray-500">No products found in database</p>
                <p className="text-sm text-gray-400 mt-1">
                  Add your first product above or visit the seed database page
                </p>
              </div>
            ) : (
              <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">
                {products.slice(0, 6).map((product) => (
                  <div key={product.id} className="border border-gray-200 rounded-lg p-4 hover:shadow-md transition-shadow">
                    <div className="aspect-square mb-3 bg-gray-100 rounded-lg overflow-hidden">
                      <img
                        src={product.images[0] || 'https://via.placeholder.com/300x300?text=No+Image'}
                        alt={product.name}
                        className="w-full h-full object-cover"
                        onError={(e) => {
                          e.currentTarget.src = 'https://via.placeholder.com/300x300?text=No+Image';
                        }}
                      />
                    </div>
                    <h3 className="font-medium text-gray-900 text-sm mb-1 line-clamp-2">
                      {product.name}
                    </h3>
                    <p className="text-xs text-gray-500 mb-2">{product.category}</p>
                    <div className="flex items-center justify-between">
                      <div>
                        <p className="font-semibold text-gray-900">${product.price.toFixed(2)}</p>
                        {product.comparePrice && (
                          <p className="text-xs text-gray-500 line-through">
                            ${product.comparePrice.toFixed(2)}
                          </p>
                        )}
                      </div>
                      <div className="text-right">
                        <p className="text-xs text-gray-500">Stock: {product.stock}</p>
                        {product.featured && (
                          <span className="text-xs bg-yellow-100 text-yellow-800 px-2 py-1 rounded-full">
                            Featured
                          </span>
                        )}
                      </div>
                    </div>
                  </div>
                ))}
              </div>
            )}
            
            {products.length > 6 && (
              <div className="mt-4 text-center">
                <button
                  onClick={() => navigate('/products')}
                  className="text-blue-600 hover:text-blue-700 font-medium text-sm"
                >
                  View all {products.length} products →
                </button>
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}
