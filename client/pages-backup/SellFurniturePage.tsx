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
  Sofa
} from 'lucide-react';
import { useProductStore } from '../store/productStore';
import { useAuthStore } from '../store';
import ImageUpload from '../components/ImageUpload';

interface FurnitureFormData {
  name: string;
  description: string;
  price: string;
  comparePrice: string;
  category: string;
  imageUrl: string;
  stock: string;
  featured: boolean;
  material: string;
  dimensions: string;
  color: string;
  style: string;
  condition: string;
  weight: string;
  roomType: string;
}

const MATERIALS = ['Wood', 'Metal', 'Leather', 'Fabric', 'Glass', 'Marble', 'Stone', 'Plastic', 'Rattan', 'Wicker', 'Bamboo', 'Upholstered', 'Velvet', 'Linen', 'Cotton', 'Microfiber', 'Faux Leather', 'Suede', 'Canvas', 'Acrylic', 'Plywood', 'MDF', 'Particle Board'];
const STYLES = ['Modern', 'Contemporary', 'Traditional', 'Rustic', 'Industrial', 'Mid-Century Modern', 'Scandinavian', 'Minimalist', 'Farmhouse', 'Coastal', 'Bohemian', 'Art Deco', 'Victorian', 'Colonial', 'Transitional', 'Eclectic', 'Mediterranean', 'Asian', 'Southwestern'];
const COLORS = ['Natural', 'Cherry', 'Oak', 'Walnut', 'Mahogany', 'Maple', 'Pine', 'Birch', 'Ash', 'Ebony', 'White', 'Black', 'Gray', 'Beige', 'Brown', 'Blue', 'Green', 'Red', 'Yellow', 'Orange', 'Purple', 'Pink'];
const CONDITIONS = ['New', 'Like New', 'Excellent', 'Good', 'Fair', 'Used', 'Vintage', 'Antique', 'Refurbished', 'Restored', 'Distressed', 'Handmade'];
const ROOM_TYPES = ['Living Room', 'Bedroom', 'Dining Room', 'Office', 'Kitchen', 'Bathroom', 'Entryway', 'Family Room', 'Home Office', 'Study', 'Library', 'Sunroom', 'Basement', 'Attic', 'Outdoor', 'Patio', 'Deck'];

export default function SellFurniturePage() {
  const navigate = useNavigate();
  const { addProduct, isLoading, error, clearError } = useProductStore();
  const { isAuthenticated } = useAuthStore();
  
  const [formData, setFormData] = useState<FurnitureFormData>({
    name: '',
    description: '',
    price: '',
    comparePrice: '',
    category: 'Furniture',
    imageUrl: '',
    stock: '',
    featured: false,
    material: '',
    dimensions: '',
    color: '',
    style: '',
    condition: '',
    weight: '',
    roomType: ''
  });
  
  const [formErrors, setFormErrors] = useState<Partial<FurnitureFormData>>({});
  const [submitSuccess, setSubmitSuccess] = useState(false);

  useEffect(() => {
    if (!isAuthenticated) {
      navigate('/login');
    }
  }, [isAuthenticated, navigate]);

  const validateForm = (): boolean => {
    const errors: Partial<FurnitureFormData> = {};

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
    if (formErrors[name as keyof FurnitureFormData]) {
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
          material: formData.material,
          dimensions: formData.dimensions,
          color: formData.color,
          style: formData.style,
          condition: formData.condition,
          weight: formData.weight,
          roomType: formData.roomType
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
          category: 'Furniture',
          imageUrl: '',
          stock: '',
          featured: false,
          material: '',
          dimensions: '',
          color: '',
          style: '',
          condition: '',
          weight: '',
          roomType: ''
        });
        setSubmitSuccess(false);
      }, 2000);

    } catch (err) {
      console.error('Failed to add furniture item:', err);
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
              <div className="w-10 h-10 bg-brown-100 rounded-lg flex items-center justify-center">
                <Sofa className="w-5 h-5 text-brown-600" />
              </div>
              <div>
                <h1 className="text-2xl font-bold text-gray-900">Sell Furniture</h1>
                <p className="text-gray-600">List your furniture and home decor</p>
              </div>
            </div>
          </div>

          {/* Success Message */}
          {submitSuccess && (
            <div className="m-6 p-4 bg-green-50 border border-green-200 rounded-lg flex items-center gap-3">
              <CheckCircle className="w-5 h-5 text-green-500" />
              <span className="text-green-700">Furniture item added successfully!</span>
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
                placeholder="e.g., Modern Leather Sofa"
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
                placeholder="Describe the furniture, condition, features, dimensions, etc."
                maxLength={1000}
              />
              {formErrors.description && (
                <p className="mt-1 text-sm text-red-600">{formErrors.description}</p>
              )}
              <p className="mt-1 text-sm text-gray-500">{formData.description.length}/1000 characters</p>
            </div>

            {/* Furniture Specific Fields */}
            <div className="space-y-4 border-t border-gray-200 pt-6">
              <h3 className="text-lg font-semibold text-gray-900 mb-4">Furniture Details</h3>
              
              <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
                {/* Material */}
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">Material</label>
                  <select
                    name="material"
                    value={formData.material}
                    onChange={handleInputChange}
                    className="w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 border-gray-300"
                  >
                    <option value="">Select material</option>
                    {MATERIALS.map(material => (
                      <option key={material} value={material}>{material}</option>
                    ))}
                  </select>
                  {formErrors.material && <p className="mt-1 text-sm text-red-600">{formErrors.material}</p>}
                </div>

                {/* Style */}
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">Style</label>
                  <select
                    name="style"
                    value={formData.style}
                    onChange={handleInputChange}
                    className="w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 border-gray-300"
                  >
                    <option value="">Select style</option>
                    {STYLES.map(style => (
                      <option key={style} value={style}>{style}</option>
                    ))}
                  </select>
                  {formErrors.style && <p className="mt-1 text-sm text-red-600">{formErrors.style}</p>}
                </div>

                {/* Color */}
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">Color</label>
                  <select
                    name="color"
                    value={formData.color}
                    onChange={handleInputChange}
                    className="w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 border-gray-300"
                  >
                    <option value="">Select color</option>
                    {COLORS.map(color => (
                      <option key={color} value={color}>{color}</option>
                    ))}
                  </select>
                  {formErrors.color && <p className="mt-1 text-sm text-red-600">{formErrors.color}</p>}
                </div>

                {/* Condition */}
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">Condition</label>
                  <select
                    name="condition"
                    value={formData.condition}
                    onChange={handleInputChange}
                    className="w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 border-gray-300"
                  >
                    <option value="">Select condition</option>
                    {CONDITIONS.map(condition => (
                      <option key={condition} value={condition}>{condition}</option>
                    ))}
                  </select>
                  {formErrors.condition && <p className="mt-1 text-sm text-red-600">{formErrors.condition}</p>}
                </div>

                {/* Room Type */}
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">Room Type</label>
                  <select
                    name="roomType"
                    value={formData.roomType}
                    onChange={handleInputChange}
                    className="w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 border-gray-300"
                  >
                    <option value="">Select room type</option>
                    {ROOM_TYPES.map(roomType => (
                      <option key={roomType} value={roomType}>{roomType}</option>
                    ))}
                  </select>
                  {formErrors.roomType && <p className="mt-1 text-sm text-red-600">{formErrors.roomType}</p>}
                </div>

                {/* Dimensions */}
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">Dimensions (L x W x H)</label>
                  <input
                    type="text"
                    name="dimensions"
                    value={formData.dimensions}
                    onChange={handleInputChange}
                    className="w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 border-gray-300"
                    placeholder="e.g., 84 x 36 x 30 inches"
                  />
                  {formErrors.dimensions && <p className="mt-1 text-sm text-red-600">{formErrors.dimensions}</p>}
                </div>

                {/* Weight */}
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">Weight</label>
                  <input
                    type="text"
                    name="weight"
                    value={formData.weight}
                    onChange={handleInputChange}
                    className="w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 border-gray-300"
                    placeholder="e.g., 75 lbs"
                  />
                  {formErrors.weight && <p className="mt-1 text-sm text-red-600">{formErrors.weight}</p>}
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
                label="Furniture Photos"
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
                Feature this furniture item on homepage
              </label>
              <p className="mt-1 text-sm text-gray-500">Featured furniture items appear in trending section</p>
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
                    Adding Furniture Item...
                  </>
                ) : (
                  <>
                    <Plus className="w-5 h-5" />
                    Add Furniture Item
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
