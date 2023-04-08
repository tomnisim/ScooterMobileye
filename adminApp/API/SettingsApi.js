import { Response } from "./Response";
import {CATCH, CONNECTION_ERROR} from "./AdvertismentsApi"
import {path} from "./Path"

const RESET_PATH = path+"reset"
const SHUT_DOWN_PATH = path+"shut_down"
const VIEW_SERVER_LOGGER_PATH = path+"view_server_logger"
const VIEW_ERROR_LOGGER_PATH = path+"view_error_logger"
const VIEW_SYSTEM_LOGGER_PATH = path+"view_system_logger"
const SET_RP_CONFIG = path+"set_rp_config"
const SET_SERVER_CONFIG = path+"set_server_config"


import axios from '../assets/AxiosInstance';

export class SettingsApi {



    reset() {
        return axios.get(RESET_PATH,
        {
            params:{}
        })
            .then(res => {
                return new Response(res.data);
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));    
    }

    shut_down() {
        return axios.get(SHUT_DOWN_PATH,
        {
            params:{}
        })
            .then(res => {
                return new Response(res.data);
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));    
    }

    view_server_logger() {
        return axios.get(VIEW_SERVER_LOGGER_PATH,
        {
            params:{}
        })
            .then(res => {
                return new Response(res.data);
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));    
    }

    view_error_logger() {
        return axios.get(VIEW_ERROR_LOGGER_PATH,
        {
            params:{}
        })
            .then(res => {
                return new Response(res.data);
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));    
    }

    view_system_logger() {
        return axios.get(VIEW_SYSTEM_LOGGER_PATH,
        {
            params:{}
        })
            .then(res => {
                return new Response(res.data);
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));    
    }

    set_rp_config() {
        return axios.get(SET_RP_CONFIG,
        {
            params:{}
        })
            .then(res => {
                return new Response(res.data);
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));    
    }

    set_server_config() {
        return axios.get(SET_SERVER_CONFIG,
        {
            params:{}
        })
            .then(res => {
                return new Response(res.data);
            })
            .catch(res => Response.create(CATCH,true, CONNECTION_ERROR ));    
    }


}