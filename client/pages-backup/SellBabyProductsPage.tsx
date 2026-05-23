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
  Baby
} from 'lucide-react';
import { useProductStore } from '../store/productStore';
import { useAuthStore } from '../store';
import ImageUpload from '../components/ImageUpload';

interface BabyProductsFormData {
  name: string;
  description: string;
  price: string;
  comparePrice: string;
  category: string;
  imageUrl: string;
  stock: string;
  featured: boolean;
  brand: string;
  ageRange: string;
  gender: string;
  size: string;
  material: string;
  safetyFeatures: string;
}

const BRANDS = ['Pampers', 'Huggies', 'Luvs', 'Huggies Little Movers', 'Pampers Swaddlers', 'The Honest Company', 'Hello Bello', 'Burt\'s Bees', 'Babyganics', 'Earth\'s Best', 'Seventh Generation', 'Johnson\'s', 'Cetaphil', 'Aveeno Baby', 'Mustela', 'Weleda', 'Aden + Anais', 'Skip Hop', 'Mamaearth', 'Ergobaby', 'Chicco', 'Fisher-Price', 'Graco', 'Britax', 'Evenflo', 'Baby Jogger', 'BOB', 'UPPAbaby'];
const AGE_RANGES = ['Newborn (0-3 months)', 'Infant (3-6 months)', 'Baby (6-12 months)', 'Toddler (1-3 years)', 'Preschooler (3-5 years)', 'All Ages', '0-6 months', '6-12 months', '12-18 months', '18-24 months', '2-3 years', '3-4 years', '4-5 years'];
const GENDERS = ['Unisex', 'Boy', 'Girl', 'Neutral'];
const SIZES = ['Preemie', 'Newborn', '0-3 months', '3-6 months', '6-9 months', '9-12 months', '12-18 months', '18-24 months', '2T', '3T', '4T', '5T', 'One Size'];
const MATERIALS = ['Organic Cotton', 'Bamboo', 'Merino Wool', 'Cotton', 'Fleece', 'Muslin', 'Velvet', 'Silk', 'Polyester', 'Microfiber', 'Terry Cloth', 'Waterproof', 'Breathable Fabric', 'Hypoallergenic', 'BPA-Free Plastic', 'Natural Rubber', 'Wood'];
const SAFETY_FEATURES = ['CPSC Certified', 'ASTM Certified', 'JPMA Certified', 'Lead-Free', 'Phthalate-Free', 'BPA-Free', 'Non-Toxic', 'Flame Retardant', 'Hypoallergenic', 'Machine Washable', 'Hand Wash Only', 'Spot Clean Only'];

export default function SellBabyProductsPage() {
  const navigate = useNavigate();
  const { addProduct, isLoading, error, clearError } = useProductStore();
  const { isAuthenticated } = useAuthStore();
  
  const [formData, setFormData] = useState<BabyProductsFormData>({
    name: '',
    description: '',
    price: '',
    comparePrice: '',
    category: 'Baby Products',
    imageUrl: '',
    stock: '',
    featured: false,
    brand: '',
    ageRange: '',
    gender: '',
    size: '',
    material: '',
    safetyFeatures: ''
  });
  
  const [formErrors, setFormErrors] = useState<Partial<BabyProductsFormData>>({});
  const [submitSuccess, setSubmitSuccess] = useState(false);

  useEffect(() => {
    if (!isAuthenticated) {
      navigate('/login');
    }
  }, [isAuthenticated, navigate]);

  const validateForm = (): boolean => {
    const errors: Partial<BabyProductsFormData> = {};

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

    if (!formData.ageRange) {
      errors.ageRange = 'Age range is required';
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
    if (formErrors[name as keyof BabyProductsFormData]) {
      setFormErrors(prev => ({
        ...prev,
        [name]: undefined
      }));
    }
  };

  const handleImageSelect = (images: string[]) => {
    const imageUrl = images.length > 0 ? images[0] : '';
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
        images: formData.imageUrl.trim() ? [formData.imageUrl.trim()] : [],
        stock: Number(formData.stock),
        rating: 0,
        reviews: 0,
        featured: formData.featured,
        categoryAttributes: {
          brand: formData.brand,
          ageRange: formData.ageRange,
          gender: (formData.gender === 'Boy' ? 'men' : formData.gender === 'Girl' ? 'women' : formData.gender === 'Unisex' || formData.gender === 'Neutral' ? 'unisex' : 'kids') as 'men' | 'women' | 'unisex' | 'kids',
          size: formData.size,
          material: formData.material,
          safetyFeatures: formData.safetyFeatures
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
          category: 'Baby Products',
          imageUrl: '',
          stock: '',
          featured: false,
          brand: '',
          ageRange: '',
          gender: '',
          size: '',
          material: '',
          safetyFeatures: ''
        });
        setSubmitSuccess(false);
      }, 2000);

    } catch (err) {
      console.error('Failed to add baby product:', err);
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
              <div className="w-10 h-10 bg-sky-100 rounded-lg flex items-center justify-center">
                <Baby className="w-5 h-5 text-sky-600" />
              </div>
              <div>
                <h1 className="text-2xl font-bold text-gray-900">Sell Baby Products</h1>
                <p className="text-gray-600">List your baby items and children's products</p>
              </div>
            </div>
          </div>

          {/* Success Message */}
          {submitSuccess && (
            <div className="m-6 p-4 bg-green-50 border border-green-200 rounded-lg flex items-center gap-3">
              <CheckCircle className="w-5 h-5 text-green-500" />
              <span className="text-green-700">Baby product added successfully!</span>
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
                placeholder="e.g., Organic Cotton Baby Onesie"
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
                placeholder="Describe the baby product, materials, features, usage, etc."
                maxLength={1000}
              />
              {formErrors.description && (
                <p className="mt-1 text-sm text-red-600">{formErrors.description}</p>
              )}
              <p className="mt-1 text-sm text-gray-500">{formData.description.length}/1000 characters</p>
            </div>

            {/* Baby Products Specific Fields */}
            <div className="space-y-4 border-t border-gray-200 pt-6">
              <h3 className="text-lg font-semibold text-gray-900 mb-4">Baby Product Details</h3>
              
              <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
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

                {/* Age Range */}
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">Age Range *</label>
                  <select
                    name="ageRange"
                    value={formData.ageRange}
                    onChange={handleInputChange}
                    className={`w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 ${
                      formErrors.ageRange ? 'border-red-500' : 'border-gray-300'
                    }`}
                  >
                    <option value="">Select age range</option>
                    {AGE_RANGES.map(ageRange => (
                      <option key={ageRange} value={ageRange}>{ageRange}</option>
                    ))}
                  </select>
                  {formErrors.ageRange && <p className="mt-1 text-sm text-red-600">{formErrors.ageRange}</p>}
                </div>

                {/* Gender */}
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">Gender</label>
                  <select
                    name="gender"
                    value={formData.gender}
                    onChange={handleInputChange}
                    className="w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 border-gray-300"
                  >
                    <option value="">Select gender</option>
                    {GENDERS.map(gender => (
                      <option key={gender} value={gender}>{gender}</option>
                    ))}
                  </select>
                  {formErrors.gender && <p className="mt-1 text-sm text-red-600">{formErrors.gender}</p>}
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

                {/* Safety Features */}
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">Safety Features</label>
                  <select
                    name="safetyFeatures"
                    value={formData.safetyFeatures}
                    onChange={handleInputChange}
                    className="w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 border-gray-300"
                  >
                    <option value="">Select safety features</option>
                    {SAFETY_FEATURES.map(feature => (
                      <option key={feature} value={feature}>{feature}</option>
                    ))}
                  </select>
                  {formErrors.safetyFeatures && <p className="mt-1 text-sm text-red-600">{formErrors.safetyFeatures}</p>}
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
                onImagesSelect={handleImageSelect}
                currentImages={formData.imageUrl ? [formData.imageUrl] : []}
                label="Baby Product Photos"
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
                Feature this baby product on homepage
              </label>
              <p className="mt-1 text-sm text-gray-500">Featured baby products appear in trending section</p>
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
                    Adding Baby Product...
                  </>
                ) : (
                  <>
                    <Plus className="w-5 h-5" />
                    Add Baby Product
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
