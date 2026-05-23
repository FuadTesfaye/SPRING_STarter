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
  FootprintsIcon as Footprint
} from 'lucide-react';
import { useProductStore } from '../store/productStore';
import { useAuthStore } from '../store';
import ImageUpload from '../components/ImageUpload';

interface ShoeFormData {
  name: string;
  description: string;
  price: string;
  comparePrice: string;
  category: string;
  imageUrl: string;
  stock: string;
  featured: boolean;
  shoeSize: string;
  shoeType: string;
  color: string;
  material: string;
  brand: string;
}

const SHOE_SIZES = ['5', '5.5', '6', '6.5', '7', '7.5', '8', '8.5', '9', '9.5', '10', '10.5', '11', '11.5', '12', '12.5', '13', '14', '15'];
const SHOE_TYPES = ['Sneakers', 'Boots', 'Sandals', 'Dress Shoes', 'Running Shoes', 'Casual Shoes', 'Sports Shoes'];
const COLORS = ['Black', 'White', 'Red', 'Blue', 'Green', 'Yellow', 'Orange', 'Purple', 'Pink', 'Brown', 'Gray', 'Navy', 'Beige'];
const MATERIALS = ['Leather', 'Synthetic', 'Canvas', 'Rubber', 'Mesh', 'Suede', 'Textile', 'Knit'];
const BRANDS = ['Nike', 'Adidas', 'Puma', 'Reebok', 'New Balance', 'Converse', 'Vans', 'Jordan', 'Under Armour', 'Skechers'];

export default function SellShoesPage() {
  const navigate = useNavigate();
  const { addProduct, isLoading, error, clearError } = useProductStore();
  const { isAuthenticated } = useAuthStore();
  
  const [formData, setFormData] = useState<ShoeFormData>({
    name: '',
    description: '',
    price: '',
    comparePrice: '',
    category: 'Shoes',
    imageUrl: '',
    stock: '',
    featured: false,
    shoeSize: '',
    shoeType: '',
    color: '',
    material: '',
    brand: ''
  });

  const SHOE_CATEGORIES = [
    { name: 'Shoes', slug: 'shoes' },
    { name: 'Men\'s Shoes', slug: 'mens-shoes' },
    { name: 'Women\'s Shoes', slug: 'womens-shoes' }
  ];
  
  const [formErrors, setFormErrors] = useState<Partial<ShoeFormData>>({});
  const [submitSuccess, setSubmitSuccess] = useState(false);

  useEffect(() => {
    if (!isAuthenticated) {
      navigate('/login');
    }
  }, [isAuthenticated, navigate]);

  const handleCategoryChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    const selectedCategory = SHOE_CATEGORIES.find(cat => cat.slug === e.target.value);
    if (selectedCategory) {
      setFormData(prev => ({ ...prev, category: selectedCategory.name }));
    }
  };

  const validateForm = (): boolean => {
    const errors: Partial<ShoeFormData> = {};

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

    if (!formData.shoeSize) {
      errors.shoeSize = 'Shoe size is required';
    }

    if (!formData.shoeType) {
      errors.shoeType = 'Shoe type is required';
    }

    if (!formData.color) {
      errors.color = 'Color is required';
    }

    if (!formData.material) {
      errors.material = 'Material is required';
    }

    if (!formData.brand) {
      errors.brand = 'Brand is required';
    }

    if (!formData.stock) {
      errors.stock = 'Stock quantity is required';
    } else if (isNaN(Number(formData.stock)) || Number(formData.stock) < 0) {
      errors.stock = 'Stock must be a non-negative number';
    }

    if (!formData.imageUrl.trim()) {
      errors.imageUrl = 'Product image is required';
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
    if (formErrors[name as keyof ShoeFormData]) {
      setFormErrors(prev => ({
        ...prev,
        [name]: undefined
      }));
    }
  };

  const handleImageSelect = (imageUrl: string) => {
    setFormData(prev => ({ ...prev, imageUrl }));

    if (formErrors.imageUrl) {
      setFormErrors(prev => ({ ...prev, imageUrl: undefined }));
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
        imageUrl: formData.imageUrl.trim(),
        stock: Number(formData.stock),
        rating: 0,
        reviews: 0,
        featured: formData.featured,
        categoryAttributes: {
          shoeSize: formData.shoeSize,
          shoeType: formData.shoeType,
          color: formData.color,
          material: formData.material,
          brand: formData.brand
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
          category: 'Shoes',
          imageUrl: '',
          stock: '',
          featured: false,
          shoeSize: '',
          shoeType: '',
          color: '',
          material: '',
          brand: ''
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
                <Footprint className="w-5 h-5 text-blue-600" />
              </div>
              <div>
                <h1 className="text-2xl font-bold text-gray-900">Sell Shoes</h1>
                <p className="text-gray-600">List your shoes for sale</p>
              </div>
            </div>
          </div>

          {/* Success Message */}
          {submitSuccess && (
            <div className="m-6 p-4 bg-green-50 border border-green-200 rounded-lg flex items-center gap-3">
              <CheckCircle className="w-5 h-5 text-green-500" />
              <span className="text-green-700">Shoes added successfully!</span>
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
            {/* Category Dropdown */}
            <div>
              <label className="flex items-center gap-2 text-sm font-medium text-gray-700 mb-2">
                <Footprint className="w-4 h-4" />
                Shoe Category *
              </label>
              <select
                value={SHOE_CATEGORIES.find(cat => cat.name === formData.category)?.slug || 'shoes'}
                onChange={handleCategoryChange}
                className="w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 border-gray-300"
              >
                {SHOE_CATEGORIES.map((category) => (
                  <option key={category.slug} value={category.slug}>
                    {category.name}
                  </option>
                ))}
              </select>
            </div>

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
                placeholder="e.g., Nike Air Max 2024"
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
                placeholder="Describe the shoes, condition, brand, features, etc."
                maxLength={1000}
              />
              {formErrors.description && (
                <p className="mt-1 text-sm text-red-600">{formErrors.description}</p>
              )}
              <p className="mt-1 text-sm text-gray-500">{formData.description.length}/1000 characters</p>
            </div>

            {/* Shoe Specific Fields */}
            <div className="space-y-4 border-t border-gray-200 pt-6">
              <h3 className="text-lg font-semibold text-gray-900 mb-4">Shoe Details</h3>
              
              <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                {/* Shoe Size */}
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">Shoe Size *</label>
                  <select
                    name="shoeSize"
                    value={formData.shoeSize}
                    onChange={handleInputChange}
                    className={`w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 ${
                      formErrors.shoeSize ? 'border-red-500' : 'border-gray-300'
                    }`}
                  >
                    <option value="">Select shoe size</option>
                    {SHOE_SIZES.map(size => (
                      <option key={size} value={size}>{size}</option>
                    ))}
                  </select>
                  {formErrors.shoeSize && <p className="mt-1 text-sm text-red-600">{formErrors.shoeSize}</p>}
                </div>

                {/* Shoe Type */}
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">Shoe Type *</label>
                  <select
                    name="shoeType"
                    value={formData.shoeType}
                    onChange={handleInputChange}
                    className={`w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 ${
                      formErrors.shoeType ? 'border-red-500' : 'border-gray-300'
                    }`}
                  >
                    <option value="">Select shoe type</option>
                    {SHOE_TYPES.map(type => (
                      <option key={type} value={type}>{type}</option>
                    ))}
                  </select>
                  {formErrors.shoeType && <p className="mt-1 text-sm text-red-600">{formErrors.shoeType}</p>}
                </div>

                {/* Color */}
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">Color *</label>
                  <select
                    name="color"
                    value={formData.color}
                    onChange={handleInputChange}
                    className={`w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 ${
                      formErrors.color ? 'border-red-500' : 'border-gray-300'
                    }`}
                  >
                    <option value="">Select color</option>
                    {COLORS.map(color => (
                      <option key={color} value={color}>{color}</option>
                    ))}
                  </select>
                  {formErrors.color && <p className="mt-1 text-sm text-red-600">{formErrors.color}</p>}
                </div>

                {/* Material */}
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">Material *</label>
                  <select
                    name="material"
                    value={formData.material}
                    onChange={handleInputChange}
                    className={`w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 ${
                      formErrors.material ? 'border-red-500' : 'border-gray-300'
                    }`}
                  >
                    <option value="">Select material</option>
                    {MATERIALS.map(material => (
                      <option key={material} value={material}>{material}</option>
                    ))}
                  </select>
                  {formErrors.material && <p className="mt-1 text-sm text-red-600">{formErrors.material}</p>}
                </div>

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
                onImageSelect={handleImageSelect}
                currentImage={formData.imageUrl}
                label="Shoe Photos"
              />
              {formErrors.imageUrl && (
                <p className="mt-1 text-sm text-red-600">{formErrors.imageUrl}</p>
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
                Feature these shoes on the homepage
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
                    Adding Shoes...
                  </>
                ) : (
                  <>
                    <Plus className="w-5 h-5" />
                    Add Shoes
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
