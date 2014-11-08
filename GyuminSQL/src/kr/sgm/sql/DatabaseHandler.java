package kr.sgm.sql;

import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.persist.EntityCursor;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;
import com.sleepycat.persist.StoreConfig;

import java.io.File;
import java.util.ArrayList;

import kr.sgm.sql.entity.Record;
import kr.sgm.sql.entity.Table;

class DatabaseHandler<K, T> {
  private static final File ENV_HOME = new File("db/");
  private static final EnvironmentConfig ENV_CONFIG = new EnvironmentConfig();
  private static final StoreConfig STORE_CONFIG = new StoreConfig();

  static {
    ENV_CONFIG.setAllowCreate(true);
    STORE_CONFIG.setAllowCreate(true);
  }

  private final String storeName;
  private final Class<K> keyType;
  private final Class<T> entityType;
  private Environment env;
  private EntityStore store;

  DatabaseHandler(String storeName, Class<K> keyType, Class<T> entityType) {
    this.storeName = storeName;
    this.keyType = keyType;
    this.entityType = entityType;
  }

  static DatabaseHandler tableHandler(String tableName) {
    return new DatabaseHandler(tableName, String.class, Record.class);
  }

  static DatabaseHandler infoHandler() {
    return new DatabaseHandler("_info", String.class, Table.class);
  }

  private void open() {
    // Environment, EntityStore 생성자가 낼 수 있는
    // Exception들을 처리해야 하지만
    // DBMS 구현에 집중하기 위해 처리하지 않겠다.
    // 즉, 실패하지 않는다고 가정한다.
    // close 메서드도 같은 가정을 한다.
    env = new Environment(ENV_HOME, ENV_CONFIG);
    store = new EntityStore(env, this.storeName, STORE_CONFIG);
  }

  private void close() {
    store.close();
    env.close();
  }

  // entity를 저장하고 성공 여부를 반환한다.
  boolean save(T entity) {
    open();
    try {
      PrimaryIndex<K, T> idx = store.getPrimaryIndex(keyType, entityType);
      idx.put(entity);
      return true;
    }catch(DatabaseException ex) {
      return false;
    }finally {
      close();
    }
  }

  // key에 해당하는 entity를 불러와 반환한다. 실패하면 null을 반환한다.
  T load(K key) {
    open();
    try {
      PrimaryIndex<K, T> idx = store.getPrimaryIndex(keyType, entityType);
      T entity = idx.get(key);
      return entity;
    }catch(DatabaseException ex) {
      return null;
    }finally {
      close();
    }
  }

  // 모든 entity를 불러와 반환한다.
  ArrayList<T> all() {
    open();
    try {
      PrimaryIndex<K, T> idx = store.getPrimaryIndex(keyType, entityType);
      EntityCursor<T> cursor = idx.entities();
      try {
        ArrayList<T> entities = new ArrayList<T>();
        for(T entity : cursor)
          entities.add(entity);
        return entities;
      }finally {
        cursor.close();
      }
    }catch(DatabaseException ex) {
      return null;
    }finally {
      close();
    }
  }

  // key에 해당하는 entity를 지우고 성공 여부를 반환한다.
  boolean remove(K key) {
    open();
    try {
      PrimaryIndex<K, T> idx = store.getPrimaryIndex(keyType, entityType);
      idx.delete(key);
      return true;
    }catch(DatabaseException ex) {
      return false;
    }finally {
      close();
    }
  }

  // 모든 entity를 지우고 성공 여부를 반환한다.
  boolean truncate() {
    open();
    try {
      store.truncateClass(entityType);
      return true;
    }catch(DatabaseException ex) {
      return false;
    }finally {
      close();
    }
  }
}
