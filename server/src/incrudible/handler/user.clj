(ns incrudible.handler.user
  (:require [ataraxy.response :as response]
            [clojure.java.jdbc :as jdbc]
            [duct.database.sql]
            [integrant.core :as ig])
  (:import (duct.database.sql Boundary)))

(defprotocol Users
  (create-user [db user])
  (list-users [db])
  (fetch-user [db id]))

(extend-protocol Users
  Boundary
  (create-user [{db :spec} user]
   (val (ffirst (jdbc/insert! db :users user))))
  (list-users [{db :spec}]
   (jdbc/query db ["SELECT * FROM users"]))
  (fetch-user [{db :spec} id]
   (first (jdbc/query db ["SELECT * FROM users WHERE id = ?" id]))))

(defmethod ig/init-key ::create [_ {:keys [db]}]
  (fn [{[_ user] :ataraxy/result}]
    (let [id (create-user db user)]
      [::response/created (str "/users/" id)])))

(defmethod ig/init-key ::list [_ {:keys [db]}]
  (fn [_] [::response/ok (list-users db)]))

(defmethod ig/init-key ::fetch [_ {:keys [db]}]
  (fn [{[_ id] :ataraxy/result}]
    (if-let [thing (fetch-user db id)]
      [::response/ok thing]
      [::response/not-found {:error :not-found}])))
