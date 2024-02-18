package com.example.ecoms.modules.product.dao

import android.content.res.AssetManager
import com.example.ecoms.common.dao.ObjectFileProc
import com.example.ecoms.common.dao.PagingFileSource
import com.example.ecoms.modules.product.model.Product

class ProductPagingSource (
  private val assetMan: AssetManager,
  private val filePath: String = "products.csv"
) : PagingFileSource<Product>(assetMan, filePath,
    // customise this for each domain class type
    ObjectFileProc<Product>(
      objectProducer =  { tokens ->
        Product(tokens[0].toInt(), tokens[1])
      }
    )) {
    // empty
}