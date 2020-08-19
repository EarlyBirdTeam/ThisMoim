import http from "../http-common2";

class TextDataService{
    getAll(){
        return http.get("/texts");
    }

    // get(id){
    //     return http.get(`/tutorials/${id}`);
    // }

    create(data){
        return http.post("/texts", data);
    }

    update(id, data){
        return http.put(`/texts/${id}`, data);
    }

    // delete(id){
    //     return http.delete(`/tutorials/${id}`);
    // }

    // deleteAll(){
    //     return http.delete(`/tutorials`);
    // }

    // findByTitle(title){
    //     return http.get(`/tutorials?title=${title}`);
    // }
}

export default new TextDataService();