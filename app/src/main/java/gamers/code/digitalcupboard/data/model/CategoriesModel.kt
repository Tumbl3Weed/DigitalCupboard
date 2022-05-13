package gamers.code.digitalcupboard.data.model

import android.graphics.drawable.Drawable

data class CategoriesModel(

    var primaryKey: String?,
    var owner: String?,
    var CategoryName: String?,
    var max: Int?,
    var current: Int?,

    ) : Comparable<CategoriesModel> {
    var image: Drawable? = null
    var items: Item? = null

    override fun toString(): String {
        return "CatagoriesModel(primaryKey=$primaryKey, CategoryName='$CategoryName', max=$max, current=$current, image=$image)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CategoriesModel

        if (primaryKey != other.primaryKey) return false
        if (owner != other.owner) return false
        if (CategoryName != other.CategoryName) return false
        if (max != other.max) return false
        if (current != other.current) return false
        if (image != other.image) return false

        return true
    }


    override fun compareTo(other: CategoriesModel): Int {
        if (CategoryName!! > other.CategoryName.toString()) return 1
        if (CategoryName!! < other.CategoryName.toString()) return -1

        return 0
    }

    override fun hashCode(): Int {
        var result = primaryKey?.hashCode() ?: 0
        result = 31 * result + (owner?.hashCode() ?: 0)
        result = 31 * result + (CategoryName?.hashCode() ?: 0)
        result = 31 * result + (max ?: 0)
        result = 31 * result + (current ?: 0)
        result = 31 * result + (image?.hashCode() ?: 0)
        result = 31 * result + (items?.hashCode() ?: 0)
        return result
    }


}
