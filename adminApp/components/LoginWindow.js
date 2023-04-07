import * as React from 'react';
import {ImageBackground, View, Text, Button, StyleSheet, TextInput} from 'react-native';
import {LoginApi } from '../API/LoginApi';
import { background } from '../API/Path';
import {get_users_list} from './UsersWindow';
import {get_admins_list} from './AdminsWindow';
import {get_advertisments_list } from './AdvertismentsWindow';
import { get_awards_list, get_emails_list } from './AwardsWindow';
import { get_questions_list } from './QuestionsWindow';
import { get_stats } from './StatisticsWindow';
import { get_hazards_list } from './HazardsWindow';

const load_data = async () => {
  await get_users_list();
  await get_admins_list();
  await get_advertisments_list();
  await get_awards_list();
  await get_emails_list();
  await get_questions_list();
  await get_stats();
  await get_hazards_list();
  // TODO : ADD RIDES & HAZARDS.
}


const loginApi = new LoginApi();



export default function LoginWindow({navigation}) {

    // todo: change details
    let user_email = "admin@gmail.com"
    let user_password = "admin"

    const setText_email = (text) => {
        user_email = text;
        }

    const setText_password = (text) => {
        user_password = text;
        }


    const login = async () => {
        let response = await loginApi.login(user_email, user_password)
        console.log(response)
        if (response.was_exception || response.was_exception == undefined){
            if (response.was_exception == undefined)
                alert("no connection")
            else
                alert(response.message)
        }
        else
        {
            load_data();
            navigation.navigate('Home')
        }
        
    }

    return (
        
        <View style={{flex:0.75, padding:10}}>
            <ImageBackground source={background} resizeMode="cover" style={styles.image}>
            <View style={{alignItems: 'left'}}>
            <Text style={{color:"#841584",padding:10}}><h1>Welcome!</h1></Text>
            <Text style={{color:'white'}}><h2>Scooter Gotcha Admin Application</h2></Text>
            <TextInput
                style={{color:'white' ,height:40,left:200}}
                placeholder="                                             User email"
                onChangeText={newText => setText_email(newText)}
                />
            <TextInput
                style={{color:'white' ,height:40}}
                placeholder="                                             User password"
                onChangeText={newText => setText_password(newText)}
                />
            </View>
            <View style={{paddingStart:30,paddingVertical:5,paddingLeft:50, paddingRight:10, width:100}}>
            <Button onPress={() => login()}  title="Login" color="#841584"/>
            </View>
            
            </ImageBackground>
        </View>
      );
    }

    const styles = StyleSheet.create({
        image: {
            flex: 1,
            justifyContent: 'center',
          },
        container: {
          flex: 1,
          padding: 50,
        },
        item: {
          padding: 20,
          fontSize: 15,
          marginTop: 5,
        }
      });
      
      
      
      
              
