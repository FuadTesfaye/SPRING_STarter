# Database Setup and Product Management

This document explains how to set up the database with products and use the enhanced SellPage functionality.

## Features Implemented

### 1. File Upload for Product Images
- **New Component**: `ImageUpload.tsx` - Drag & drop or click to upload images
- **Features**: 
  - File validation (image types only, max 5MB)
  - Image preview
  - Local file handling (data URLs for now)
  - Easy removal of uploaded images

### 2. Database Connection
- **New Service**: `supabaseService.ts` - Direct Supabase integration
- **Features**:
  - Connects to your Supabase database
  - Full CRUD operations for products
  - Category management
  - Product search and filtering
  - Fallback to existing services if Supabase is unavailable

### 3. Database Seeding
- **New Service**: `seedService.ts` - Automated product generation
- **New Page**: `/seed-database` - Admin interface for seeding
- **Features**:
  - Creates 15 product categories automatically
  - Generates realistic product data (names, descriptions, prices)
  - Uses random images from Picsum Photos
  - Configurable product count (default: 5,000)
  - Batch processing for performance

### 4. Enhanced SellPage
- **Updated**: `SellPage.tsx` - Now shows existing products
- **Features**:
  - Display up to 6 recent products from database
  - Shows product count and category
  - Navigate to full product catalog
  - Improved file upload integration
  - Real-time product updates

## How to Use

### Step 1: Set Up Supabase
1. Make sure your `.env` file contains valid Supabase credentials:
   ```
   VITE_SUPABASE_URL=your_supabase_url
   VITE_SUPABASE_PUBLISHABLE_KEY=your_supabase_key
   ```

### Step 2: Seed the Database
1. Navigate to `/seed-database` in your app
2. Choose the number of products to generate (recommended: 5,000)
3. Click "Seed Database" button
4. Wait for the process to complete (may take a few minutes)

### Step 3: Use the SellPage
1. Navigate to `/sell` in your app
2. You'll see:
   - Product creation form with file upload
   - Existing products displayed below
   - Option to view all products

### Step 4: Add New Products
1. Fill in the product details
2. Upload an image using the drag & drop interface
3. Select category and pricing
4. Click "Add Product"
5. The product will be saved to Supabase and appear in the list

## Database Schema

The system uses the following Supabase tables:
- `products` - Main product information
- `categories` - Product categories
- `product_reviews` - Customer reviews
- `shopping_carts` - User shopping carts
- `cart_items` - Items in shopping carts
- `orders` - Customer orders
- `order_items` - Items in orders

## Service Architecture

The app uses a hybrid service approach:
1. **Supabase** (primary) - Direct database connection
2. **Backend API** (fallback) - REST API services
3. **Mock Service** (last resort) - Local mock data

This ensures the app works even if some services are unavailable.

## Product Generation Details

Generated products include:
- **Names**: Combinations of adjectives, materials, and product types
- **Categories**: 15 pre-defined categories (Electronics, Clothing, etc.)
- **Pricing**: Random prices between $10-$910
- **Images**: Random images from Picsum Photos
- **Inventory**: Random stock levels (1-1000 units)
- **Features**: 5% of products are marked as featured

## File Upload Notes

Currently, the file upload uses data URLs for simplicity. In production, you should:
1. Set up Supabase Storage or similar service
2. Upload files to storage service
3. Store the storage URL in the database
4. Handle file cleanup and optimization

## Troubleshooting

### Database Connection Issues
- Check your Supabase credentials in `.env`
- Ensure your Supabase project is active
- Verify RLS policies allow public read access to products

### Seeding Issues
- Make sure categories are created first
- Check Supabase permissions
- Monitor browser console for errors

### Image Upload Issues
- Ensure file size is under 5MB
- Check that file is a valid image type
- Verify browser supports FileReader API

## Next Steps

To enhance this further:
1. Implement Supabase Storage for file uploads
2. Add product editing capabilities
3. Implement bulk product operations
4. Add product analytics and reporting
5. Set up automated image optimization
