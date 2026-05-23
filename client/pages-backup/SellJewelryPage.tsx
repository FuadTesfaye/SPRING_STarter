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
  Gem
} from 'lucide-react';
import { useProductStore } from '../store/productStore';
import { useAuthStore } from '../store';
import ImageUpload from '../components/ImageUpload';

interface JewelryFormData {
  name: string;
  description: string;
  price: string;
  comparePrice: string;
  category: string;
  imageUrl: string;
  stock: string;
  featured: boolean;
  material: string;
  size: string;
  color: string;
  brand: string;
  gemstone: string;
  metalType: string;
  condition: string;
}

const MATERIALS = ['Gold', 'Silver', 'Platinum', 'White Gold', 'Rose Gold', 'Yellow Gold', 'Titanium', 'Stainless Steel', 'Sterling Silver', 'Brass', 'Bronze', 'Copper', 'Palladium', 'Tungsten', 'Ceramic', 'Wood', 'Leather', 'Fabric', 'Beads', 'Crystal', 'Glass'];
const SIZES = ['Ring Size 3', 'Ring Size 3.5', 'Ring Size 4', 'Ring Size 4.5', 'Ring Size 5', 'Ring Size 5.5', 'Ring Size 6', 'Ring Size 6.5', 'Ring Size 7', 'Ring Size 7.5', 'Ring Size 8', 'Ring Size 8.5', 'Ring Size 9', 'Ring Size 9.5', 'Ring Size 10', 'Ring Size 10.5', 'Ring Size 11', 'Ring Size 11.5', 'Ring Size 12', 'Ring Size 12.5', 'Ring Size 13', 'Ring Size 13.5', 'Ring Size 14', 'Ring Size 14.5', 'Ring Size 15', 'Ring Size 15.5', 'Ring Size 16', 'Ring Size 16.5', 'Ring Size 17', 'Ring Size 17.5', 'Ring Size 18', 'One Size', 'Small', 'Medium', 'Large', 'Extra Large', 'Adjustable'];
const COLORS = ['Gold', 'Silver', 'Rose Gold', 'White Gold', 'Yellow Gold', 'Black', 'Blue', 'Red', 'Green', 'Purple', 'Pink', 'Orange', 'Brown', 'Gray', 'White', 'Clear', 'Rainbow', 'Multi-color', 'Two-tone', 'Tri-color'];
const BRANDS = ['Tiffany & Co.', 'Cartier', 'Van Cleef & Arpels', 'Harry Winston', 'Bulgari', 'Graff', 'Bvlgari', 'Chopard', 'Piaget', 'David Yurman', 'Boucheron', 'Mikimoto', 'Pandora', 'Swarovski', 'Kay Jewelers', 'Zales', 'Blue Nile', 'James Allen', 'Brilliant Earth', 'Vrai', 'Mejuri', 'Catbird', 'Aurate', 'Gorjana'];
const GEMSTONES = ['Diamond', 'Ruby', 'Sapphire', 'Emerald', 'Aquamarine', 'Topaz', 'Amethyst', 'Garnet', 'Peridot', 'Opal', 'Turquoise', 'Moonstone', 'Citrine', 'Tourmaline', 'Tanzanite', 'Morganite', 'Pearls', 'Cubic Zirconia', 'Moissanite'];
const CONDITIONS = ['New', 'Like New', 'Excellent', 'Good', 'Fair', 'Used', 'Vintage', 'Antique', 'Estate', 'Pre-owned'];

export default function SellJewelryPage() {
  const navigate = useNavigate();
  const { addProduct, isLoading, error, clearError } = useProductStore();
  const { isAuthenticated } = useAuthStore();
  
  const [formData, setFormData] = useState<JewelryFormData>({
    name: '',
    description: '',
    price: '',
    comparePrice: '',
    category: 'Jewelry',
    imageUrl: '',
    stock: '',
    featured: false,
    material: '',
    size: '',
    color: '',
    brand: '',
    gemstone: '',
    metalType: '',
    condition: ''
  });
  
  const [formErrors, setFormErrors] = useState<Partial<JewelryFormData>>({});
  const [submitSuccess, setSubmitSuccess] = useState(false);

  useEffect(() => {
    if (!isAuthenticated) {
      navigate('/login');
    }
  }, [isAuthenticated, navigate]);

  const validateForm = (): boolean => {
    const errors: Partial<JewelryFormData> = {};

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
    if (formErrors[name as keyof JewelryFormData]) {
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
          size: formData.size,
          color: formData.color,
          brand: formData.brand,
          gemstone: formData.gemstone,
          metalType: formData.metalType,
          condition: formData.condition
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
          category: 'Jewelry',
          imageUrl: '',
          stock: '',
          featured: false,
          material: '',
          size: '',
          color: '',
          brand: '',
          gemstone: '',
          metalType: '',
          condition: ''
        });
        setSubmitSuccess(false);
      }, 2000);

    } catch (err) {
      console.error('Failed to add jewelry item:', err);
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
              <div className="w-10 h-10 bg-violet-100 rounded-lg flex items-center justify-center">
                <Gem className="w-5 h-5 text-violet-600" />
              </div>
              <div>
                <h1 className="text-2xl font-bold text-gray-900">Sell Jewelry</h1>
                <p className="text-gray-600">List your jewelry and accessories</p>
              </div>
            </div>
          </div>

          {/* Success Message */}
          {submitSuccess && (
            <div className="m-6 p-4 bg-green-50 border border-green-200 rounded-lg flex items-center gap-3">
              <CheckCircle className="w-5 h-5 text-green-500" />
              <span className="text-green-700">Jewelry item added successfully!</span>
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
                placeholder="e.g., Diamond Engagement Ring"
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
                placeholder="Describe the jewelry, materials, condition, features, etc."
                maxLength={1000}
              />
              {formErrors.description && (
                <p className="mt-1 text-sm text-red-600">{formErrors.description}</p>
              )}
              <p className="mt-1 text-sm text-gray-500">{formData.description.length}/1000 characters</p>
            </div>

            {/* Jewelry Specific Fields */}
            <div className="space-y-4 border-t border-gray-200 pt-6">
              <h3 className="text-lg font-semibold text-gray-900 mb-4">Jewelry Details</h3>
              
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

                {/* Size */}
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">Size</label>
                  <select
                    name="size"
                    value={formData.size}
                    onChange={handleInputChange}
                    className="w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 border-gray-300"
                  >
                    <option value="">Select size</option>
                    {SIZES.map(size => (
                      <option key={size} value={size}>{size}</option>
                    ))}
                  </select>
                  {formErrors.size && <p className="mt-1 text-sm text-red-600">{formErrors.size}</p>}
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

                {/* Brand */}
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">Brand</label>
                  <select
                    name="brand"
                    value={formData.brand}
                    onChange={handleInputChange}
                    className="w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 border-gray-300"
                  >
                    <option value="">Select brand</option>
                    {BRANDS.map(brand => (
                      <option key={brand} value={brand}>{brand}</option>
                    ))}
                  </select>
                  {formErrors.brand && <p className="mt-1 text-sm text-red-600">{formErrors.brand}</p>}
                </div>

                {/* Gemstone */}
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">Gemstone</label>
                  <select
                    name="gemstone"
                    value={formData.gemstone}
                    onChange={handleInputChange}
                    className="w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 border-gray-300"
                  >
                    <option value="">Select gemstone</option>
                    {GEMSTONES.map(gemstone => (
                      <option key={gemstone} value={gemstone}>{gemstone}</option>
                    ))}
                  </select>
                  {formErrors.gemstone && <p className="mt-1 text-sm text-red-600">{formErrors.gemstone}</p>}
                </div>

                {/* Metal Type */}
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">Metal Type</label>
                  <select
                    name="metalType"
                    value={formData.metalType}
                    onChange={handleInputChange}
                    className="w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 border-gray-300"
                  >
                    <option value="">Select metal type</option>
                    {MATERIALS.filter(m => m.includes('Gold') || m.includes('Silver') || m.includes('Platinum') || m.includes('Steel') || m.includes('Titanium')).map(metal => (
                      <option key={metal} value={metal}>{metal}</option>
                    ))}
                  </select>
                  {formErrors.metalType && <p className="mt-1 text-sm text-red-600">{formErrors.metalType}</p>}
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
                label="Jewelry Photos"
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
                Feature this jewelry item on homepage
              </label>
              <p className="mt-1 text-sm text-gray-500">Featured jewelry items appear in trending section</p>
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
                    Adding Jewelry Item...
                  </>
                ) : (
                  <>
                    <Plus className="w-5 h-5" />
                    Add Jewelry Item
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
