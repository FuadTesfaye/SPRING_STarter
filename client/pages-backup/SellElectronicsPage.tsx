import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { 
  Plus, 
  CheckCircle, 
  AlertCircle,
  Package,
  DollarSign,
  FileText,
  Box,
  Monitor
} from 'lucide-react';
import { useProductStore } from '../store/productStore';
import { useAuthStore } from '../store';
import ImageUpload from '../components/ImageUpload';

interface ElectronicsFormData {
  name: string;
  description: string;
  price: string;
  comparePrice: string;
  category: string;
  images: string[];
  stock: string;
  featured: boolean;
  brand: string;
  model: string;
  warranty: string;
  weight: string;
  dimensions: string;
}

interface ElectronicsFormErrors {
  name?: string;
  description?: string;
  price?: string;
  comparePrice?: string;
  category?: string;
  images?: string;
  stock?: string;
  featured?: string;
  brand?: string;
  model?: string;
  warranty?: string;
  weight?: string;
  dimensions?: string;
}

const BRANDS = ['Apple', 'Samsung', 'Sony', 'LG', 'Microsoft', 'Dell', 'HP', 'Lenovo', 'ASUS', 'Toshiba', 'Canon', 'Nikon', 'Bose'];
const WARRANTIES = ['1 Year', '2 Years', '3 Years', '5 Years', 'No Warranty', 'Manufacturer Warranty'];

export default function SellElectronicsPage() {
  const navigate = useNavigate();
  const { addProduct, isLoading, error, clearError } = useProductStore();
  const { isAuthenticated } = useAuthStore();
  
  const [formData, setFormData] = useState<ElectronicsFormData>({
    name: '',
    description: '',
    price: '',
    comparePrice: '',
    category: 'Electronics',
    images: [],
    stock: '',
    featured: false,
    brand: '',
    model: '',
    warranty: '',
    weight: '',
    dimensions: ''
  });
  
  const [formErrors, setFormErrors] = useState<ElectronicsFormErrors>({});
  const [submitSuccess, setSubmitSuccess] = useState(false);

  useEffect(() => {
    if (!isAuthenticated) {
      navigate('/login');
    }
  }, [isAuthenticated, navigate]);

  const validateForm = (): boolean => {
    const errors: ElectronicsFormErrors = {};
    console.log('=== SELL ELECTRONICS PAGE VALIDATION ===');
    console.log('Form data during validation:', formData);
    console.log('Images array:', formData.images);
    console.log('Images length:', formData.images.length);
    console.log('Current page: SellElectronicsPage.tsx');

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

    if (!formData.brand) {
      errors.brand = 'Brand is required';
    }

    if (!formData.model) {
      errors.model = 'Model is required';
    }

    if (!formData.stock) {
      errors.stock = 'Stock quantity is required';
    } else if (isNaN(Number(formData.stock)) || Number(formData.stock) < 0) {
      errors.stock = 'Stock must be a non-negative number';
    }

    // Temporarily bypass image validation for testing
    // if (formData.images.length === 0) {
    //   errors.images = 'At least one product image is required';
    // }

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
    if (formErrors[name as keyof ElectronicsFormErrors]) {
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
        categoryAttributes: {
          brand: formData.brand,
          model: formData.model,
          warranty: formData.warranty,
          weight: formData.weight,
          dimensions: formData.dimensions
        }
      };

      await addProduct(productData);
      setSubmitSuccess(true);
      
      // Reset form after 2 seconds
      setTimeout(() => {
        setFormData({
          name: '',
          description: '',
          price: '',
          comparePrice: '',
          category: 'Electronics',
          images: [],
          stock: '',
          featured: false,
          brand: '',
          model: '',
          warranty: '',
          weight: '',
          dimensions: ''
        });
        setSubmitSuccess(false);
      }, 2000);

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
                <Monitor className="w-5 h-5 text-blue-600" />
              </div>
              <div>
                <h1 className="text-2xl font-bold text-gray-900">Sell Electronics</h1>
                <p className="text-gray-600">List your electronic devices for sale</p>
              </div>
            </div>
          </div>

          {/* Success Message */}
          {submitSuccess && (
            <div className="m-6 p-4 bg-green-50 border border-green-200 rounded-lg flex items-center gap-3">
              <CheckCircle className="w-5 h-5 text-green-500" />
              <span className="text-green-700">Electronics added successfully!</span>
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
            {/* Product Name */}
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
                placeholder="e.g., iPhone 15 Pro Max"
                maxLength={100}
              />
              {formErrors.name && (
                <p className="mt-1 text-sm text-red-600">{formErrors.name}</p>
              )}
            </div>

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
                placeholder="Describe the electronic device, condition, specifications, features, etc."
                maxLength={1000}
              />
              {formErrors.description && (
                <p className="mt-1 text-sm text-red-600">{formErrors.description}</p>
              )}
              <p className="mt-1 text-sm text-gray-500">{formData.description.length}/1000 characters</p>
            </div>

            {/* Electronics Specific Fields */}
            <div className="space-y-4 border-t border-gray-200 pt-6">
              <h3 className="text-lg font-semibold text-gray-900 mb-4">Device Details</h3>
              
              <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                {/* Brand */}
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">Brand *</label>
                  <select
                    name="brand"
                    value={formData.brand}
                    onChange={handleInputChange}
                    className={`w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 ${
                      formErrors.brand ? 'border-red-500' : 'border-gray-300'
                    }`}
                  >
                    <option value="">Select brand</option>
                    {BRANDS.map(brand => (
                      <option key={brand} value={brand}>{brand}</option>
                    ))}
                  </select>
                  {formErrors.brand && <p className="mt-1 text-sm text-red-600">{formErrors.brand}</p>}
                </div>

                {/* Model */}
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">Model *</label>
                  <input
                    type="text"
                    name="model"
                    value={formData.model}
                    onChange={handleInputChange}
                    className={`w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 ${
                      formErrors.model ? 'border-red-500' : 'border-gray-300'
                    }`}
                    placeholder="e.g., iPhone 15 Pro Max"
                  />
                  {formErrors.model && <p className="mt-1 text-sm text-red-600">{formErrors.model}</p>}
                </div>

                {/* Warranty */}
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">Warranty</label>
                  <select
                    name="warranty"
                    value={formData.warranty}
                    onChange={handleInputChange}
                    className={`w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 ${
                      formErrors.warranty ? 'border-red-500' : 'border-gray-300'
                    }`}
                  >
                    <option value="">Select warranty</option>
                    {WARRANTIES.map(warranty => (
                      <option key={warranty} value={warranty}>{warranty}</option>
                    ))}
                  </select>
                  {formErrors.warranty && <p className="mt-1 text-sm text-red-600">{formErrors.warranty}</p>}
                </div>

                {/* Weight */}
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">Weight</label>
                  <input
                    type="text"
                    name="weight"
                    value={formData.weight}
                    onChange={handleInputChange}
                    className={`w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 ${
                      formErrors.weight ? 'border-red-500' : 'border-gray-300'
                    }`}
                    placeholder="e.g., 200g"
                  />
                  {formErrors.weight && <p className="mt-1 text-sm text-red-600">{formErrors.weight}</p>}
                </div>

                {/* Dimensions */}
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">Dimensions</label>
                  <input
                    type="text"
                    name="dimensions"
                    value={formData.dimensions}
                    onChange={handleInputChange}
                    className={`w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 ${
                      formErrors.dimensions ? 'border-red-500' : 'border-gray-300'
                    }`}
                    placeholder="e.g., 15.5 x 7.6 x 0.7 cm"
                  />
                  {formErrors.dimensions && <p className="mt-1 text-sm text-red-600">{formErrors.dimensions}</p>}
                </div>
              </div>
            </div>

            {/* Price and Stock */}
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

            {/* Image Upload */}
            <div>
              <ImageUpload
                onImagesSelect={handleImagesSelect}
                currentImages={formData.images}
                label="Device Photos"
              />
              {formErrors.images && (
                <p className="mt-1 text-sm text-red-600">{formErrors.images}</p>
              )}
            </div>

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
                Feature this device on the homepage
              </label>
              <p className="mt-1 text-sm text-gray-500">Featured products appear in the trending section</p>
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
                    Adding Electronics...
                  </>
                ) : (
                  <>
                    <Plus className="w-5 h-5" />
                    Add Electronics
                  </>
                )}
              </button>
              
              <button
                type="button"
                onClick={() => navigate('/sell')}
                className="px-6 py-3 border border-gray-300 text-gray-700 font-medium rounded-lg hover:bg-gray-50 transition-colors"
              >
                Back
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
}
