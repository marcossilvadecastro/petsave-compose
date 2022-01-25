package com.raywenderlich.android.petsave.common.data.api

object ApiConfiguration {
  const val BASE_ENDPOINT = "https://api.petfinder.com/v2/"
  const val AUTH_ENDPOINT = "oauth2/token/"
  const val ANIMALS_ROUTE = "animals"

  const val KEY = "Bq0bhoiEtT5iToSlbs04UoVnhx3P7tkiyC2JhUYVo07rhQCTW3"
  const val SECRET = "3pcrlrHRLSlAZpyCYy64QV6DSqxuW2ZBLcNeDuON"
}

object ApiParameters {
  const val TOKEN_TYPE = "Bearer "
  const val AUTH_HEADER = "Authorization"
  const val GRANT_TYPE_KEY = "grant_type"
  const val GRANT_TYPE_VALUE = "client_credentials"
  const val CLIENT_ID = "client_id"
  const val CLIENT_SECRET = "client_secret"

  const val PAGE = "page"
  const val LIMIT = "limit"
  const val LOCATION = "location"
  const val DISTANCE = "distance"

  const val NAME = "name"
  const val AGE = "age"
  const val TYPE = "type"
}