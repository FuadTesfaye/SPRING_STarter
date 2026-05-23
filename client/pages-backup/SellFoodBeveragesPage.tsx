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
  Coffee
} from 'lucide-react';
import { useProductStore } from '../store/productStore';
import { useAuthStore } from '../store';
import ImageUpload from '../components/ImageUpload';

interface FoodBeveragesFormData {
  name: string;
  description: string;
  price: string;
  comparePrice: string;
  category: string;
  imageUrl: string;
  stock: string;
  featured: boolean;
  brand: string;
  expirationDate: string;
  ingredients: string;
  nutritionInfo: string;
  allergens: string;
  dietaryRestrictions: string;
  packagingType: string;
  weight: string;
  volume: string;
}

const FOOD_TYPES = ['Snacks', 'Beverages', 'Dairy', 'Bakery', 'Frozen Foods', 'Canned Goods', 'Pasta & Grains', 'Produce', 'Meat & Seafood', 'Confectionery', 'Condiments', 'Spices', 'Breakfast Foods', 'Organic', 'Gluten-Free', 'Vegan', 'Vegetarian', 'International Cuisine'];
const BRANDS = ['Nestlé', 'Coca-Cola', 'PepsiCo', 'Unilever', 'Kellogg\'s', 'General Mills', 'Campbell\'s', 'ConAgra', 'Danone', 'Hershey\'s', 'Mars', 'Mondelez', 'Kraft', 'Heinz', 'McCormick', 'Del Monte', 'Pepsi', 'Dr Pepper', 'Keurig', 'Starbucks', 'Dunkin\' Donuts'];
const PACKAGING_TYPES = ['Bottle', 'Can', 'Box', 'Bag', 'Pouch', 'Carton', 'Glass Jar', 'Plastic Container', 'Paper Wrapper', 'Vacuum Sealed', 'Bulk Packaging'];
const DIETARY_RESTRICTIONS = ['None', 'Gluten-Free', 'Dairy-Free', 'Nut-Free', 'Vegan', 'Vegetarian', 'Halal', 'Kosher', 'Organic', 'Non-GMO', 'Sugar-Free', 'Low-Sodium', 'Low-Fat', 'Keto', 'Paleo'];

export default function SellFoodBeveragesPage() {
  const navigate = useNavigate();
  const { addProduct, isLoading, error, clearError } = useProductStore();
  const { isAuthenticated } = useAuthStore();
  
  const [formData, setFormData] = useState<FoodBeveragesFormData>({
    name: '',
    description: '',
    price: '',
    comparePrice: '',
    category: 'Food & Beverages',
    imageUrl: '',
    stock: '',
    featured: false,
    brand: '',
    expirationDate: '',
    ingredients: '',
    nutritionInfo: '',
    allergens: '',
    dietaryRestrictions: '',
    packagingType: '',
    weight: '',
    volume: ''
  });
  
  const [formErrors, setFormErrors] = useState<Partial<FoodBeveragesFormData>>({});
  const [submitSuccess, setSubmitSuccess] = useState(false);

  useEffect(() => {
    if (!isAuthenticated) {
      navigate('/login');
    }
  }, [isAuthenticated, navigate]);

  const validateForm = (): boolean => {
    const errors: Partial<FoodBeveragesFormData> = {};

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
    if (formErrors[name as keyof FoodBeveragesFormData]) {
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
          brand: formData.brand,
          expirationDate: formData.expirationDate,
          ingredients: formData.ingredients,
          nutritionInfo: formData.nutritionInfo,
          allergens: formData.allergens,
          dietaryRestrictions: formData.dietaryRestrictions,
          packagingType: formData.packagingType,
          weight: formData.weight,
          volume: formData.volume
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
          category: 'Food & Beverages',
          imageUrl: '',
          stock: '',
          featured: false,
          brand: '',
          expirationDate: '',
          ingredients: '',
          nutritionInfo: '',
          allergens: '',
          dietaryRestrictions: '',
          packagingType: '',
          weight: '',
          volume: ''
        });
        setSubmitSuccess(false);
      }, 2000);

    } catch (err) {
      console.error('Failed to add food & beverage product:', err);
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
              <div className="w-10 h-10 bg-amber-100 rounded-lg flex items-center justify-center">
                <Coffee className="w-5 h-5 text-amber-600" />
              </div>
              <div>
                <h1 className="text-2xl font-bold text-gray-900">Sell Food & Beverages</h1>
                <p className="text-gray-600">List your food items and beverages</p>
              </div>
            </div>
          </div>

          {/* Success Message */}
          {submitSuccess && (
            <div className="m-6 p-4 bg-green-50 border border-green-200 rounded-lg flex items-center gap-3">
              <CheckCircle className="w-5 h-5 text-green-500" />
              <span className="text-green-700">Food & beverage product added successfully!</span>
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
                placeholder="e.g., Organic Coffee Beans"
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
                placeholder="Describe the food item, taste, origin, preparation, etc."
                maxLength={1000}
              />
              {formErrors.description && (
                <p className="mt-1 text-sm text-red-600">{formErrors.description}</p>
              )}
              <p className="mt-1 text-sm text-gray-500">{formData.description.length}/1000 characters</p>
            </div>

            {/* Food & Beverages Specific Fields */}
            <div className="space-y-4 border-t border-gray-200 pt-6">
              <h3 className="text-lg font-semibold text-gray-900 mb-4">Food & Beverage Details</h3>
              
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

                {/* Expiration Date */}
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">Expiration Date</label>
                  <input
                    type="date"
                    name="expirationDate"
                    value={formData.expirationDate}
                    onChange={handleInputChange}
                    className="w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 border-gray-300"
                  />
                  {formErrors.expirationDate && <p className="mt-1 text-sm text-red-600">{formErrors.expirationDate}</p>}
                </div>

                {/* Ingredients */}
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">Ingredients</label>
                  <textarea
                    name="ingredients"
                    value={formData.ingredients}
                    onChange={handleInputChange}
                    rows={3}
                    className="w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 border-gray-300"
                    placeholder="List main ingredients (e.g., Coffee, Water, Sugar)"
                  />
                  {formErrors.ingredients && <p className="mt-1 text-sm text-red-600">{formErrors.ingredients}</p>}
                </div>

                {/* Nutrition Info */}
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">Nutrition Information</label>
                  <textarea
                    name="nutritionInfo"
                    value={formData.nutritionInfo}
                    onChange={handleInputChange}
                    rows={3}
                    className="w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 border-gray-300"
                    placeholder="e.g., Calories: 5, Protein: 0.1g, Carbs: 1.2g per serving"
                  />
                  {formErrors.nutritionInfo && <p className="mt-1 text-sm text-red-600">{formErrors.nutritionInfo}</p>}
                </div>

                {/* Allergens */}
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">Allergens</label>
                  <input
                    type="text"
                    name="allergens"
                    value={formData.allergens}
                    onChange={handleInputChange}
                    className="w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 border-gray-300"
                    placeholder="e.g., Contains nuts, dairy, soy"
                  />
                  {formErrors.allergens && <p className="mt-1 text-sm text-red-600">{formErrors.allergens}</p>}
                </div>

                {/* Dietary Restrictions */}
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">Dietary Restrictions</label>
                  <select
                    name="dietaryRestrictions"
                    value={formData.dietaryRestrictions}
                    onChange={handleInputChange}
                    className="w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 border-gray-300"
                  >
                    <option value="">Select dietary restriction</option>
                    {DIETARY_RESTRICTIONS.map(restriction => (
                      <option key={restriction} value={restriction}>{restriction}</option>
                    ))}
                  </select>
                  {formErrors.dietaryRestrictions && <p className="mt-1 text-sm text-red-600">{formErrors.dietaryRestrictions}</p>}
                </div>

                {/* Packaging Type */}
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">Packaging Type</label>
                  <select
                    name="packagingType"
                    value={formData.packagingType}
                    onChange={handleInputChange}
                    className="w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 border-gray-300"
                  >
                    <option value="">Select packaging type</option>
                    {PACKAGING_TYPES.map(packaging => (
                      <option key={packaging} value={packaging}>{packaging}</option>
                    ))}
                  </select>
                  {formErrors.packagingType && <p className="mt-1 text-sm text-red-600">{formErrors.packagingType}</p>}
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
                    placeholder="e.g., 500g"
                  />
                  {formErrors.weight && <p className="mt-1 text-sm text-red-600">{formErrors.weight}</p>}
                </div>

                {/* Volume */}
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-2">Volume</label>
                  <input
                    type="text"
                    name="volume"
                    value={formData.volume}
                    onChange={handleInputChange}
                    className="w-full px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 border-gray-300"
                    placeholder="e.g., 16 oz"
                  />
                  {formErrors.volume && <p className="mt-1 text-sm text-red-600">{formErrors.volume}</p>}
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
                label="Food & Beverage Photos"
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
                Feature this food item on homepage
              </label>
              <p className="mt-1 text-sm text-gray-500">Featured food items appear in trending section</p>
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
                    Adding Food Item...
                  </>
                ) : (
                  <>
                    <Plus className="w-5 h-5" />
                    Add Food Item
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
