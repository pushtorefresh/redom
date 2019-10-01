package com.pushtorefresh.redom.api

/**
 * Registry for View IDs.
 * Guarantees ID uniqueness within an instance of `IdRegistry`.
 */
interface IdRegistry<IdType> {
    fun mapToInt(id: IdType?): Int
}
