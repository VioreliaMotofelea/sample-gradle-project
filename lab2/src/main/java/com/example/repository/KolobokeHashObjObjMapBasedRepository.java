package com.example.repository;


import com.koloboke.collect.map.hash.HashObjObjMap;
import com.koloboke.collect.map.hash.HashObjObjMaps;

public class KolobokeHashObjObjMapBasedRepository<T> implements InMemoryRepository<T> {
  private final HashObjObjMap<Integer, T> map = HashObjObjMaps.newMutableMap();

  public KolobokeHashObjObjMapBasedRepository() {}

  @Override
  public void add(T item) {
    map.put(item.hashCode(), item);
  }

  @Override
  public boolean contains(T item) {
    return map.containsKey(item.hashCode());
  }

  @Override
  public void remove(T item) {
    map.remove(item.hashCode());
  }
}
