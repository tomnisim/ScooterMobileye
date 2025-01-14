import React,{ useState } from 'react';
import { useEffect } from 'react';
import { ImageBackground, View, Text, Button, StyleSheet, TextInput, ScrollView, TouchableOpacity} from 'react-native';
import Table from 'rc-table';
import Select from 'react-select'
import { background } from '../API/Path';
import { HazardsApi } from '../API/HazardsApi';
import {set_junctions} from './VisualHazards';


const hazardsApi = new HazardsApi();



const hazards_types_list = [{value: "pothole", label:"Pothole"}, {value: "PoleTree", label:"Pole Tree"}, {value: "RoadSign", label:"Road Sign"}]
const cities_list = [{value: "Tel-Aviv", label:"Tel Aviv"}, {value: "Haifa", label:"Haifa"}, {value: "Beer-Sheba", label:"Beer-Sheba"},{value: "Netanya", label:"Netanya"}]




export default function HazardsWindow({navigation}) {

  const [hazards_list, setHazards_list] = useState([])
  const [hazards_ids_list, setHazards_ids_list] = useState([])
  const [lng, setText_to_lng] = useState("")
  const [lat, setText_to_lat] = useState("")
  const [city, setText_to_city] = useState("")
  const [type, setText_to_type] = useState("")
  const [size, setText_to_size] = useState("")
  const [hazard_to_remove, setText_to_remove_hazard] = useState("")
  const [hazard_to_report, setText_to_report_hazard] = useState("")
  const [city_to_show, setCity_to_show] = useState("")


  async function get_hazards_list(){
    let response = await hazardsApi.view_hazards();
    if (!response.was_exception){
      setHazards_list(response.value)
      let temp = response.value
      let temp1 = temp.map((item) => {
        return (
          {key: item.id}
        );
      })
      let list_temp = []
      temp1.map((item) => list_temp.push({value: parseInt(item.key), label: item.key}))
      setHazards_ids_list(list_temp)
    }
    console.log(response.value)
  }

  useEffect(() => {
    get_hazards_list();
  }, {})


  const add_hazard = async () => {
    if (lng == "" || lat == "" || city == "" || type == "" || size == ""){
      alert("Please Enter Details.")
    }
    else
    {
      let response = await hazardsApi.add_hazard(lng, lat, city, type, size);
      if (response.was_exception){
        alert("The system cant complete your request, please try again later.")
      }
      else
      {
        alert("Hazard has been successfully added to the system.");
        get_hazards_list();

      }
    }
    
  }

  const delete_hazard = async () => {
    if (hazard_to_remove == "")
      {
        alert("Please choose Hazard")
      }
      else
      {
        let response = await hazardsApi.delete_hazard(hazard_to_remove);
        if (response.was_exception){
          alert("The system cant complete your request, please try again later.")
        }
        else
        {
          alert("Hazard has been successfully deleted from the system.");
          get_hazards_list();
  
        }

      }
    get_hazards_list();
  }

  const report_hazard = async () => {
    if (hazard_to_report == ""){
      alert("Please choose Hazard")
    }
    else{
      let response = await hazardsApi.report_hazard(hazard_to_report)
      if (response.was_exception){
        alert("The system cant complete your request, please try again later.")
      }
      else
      {
        alert("Hazard has been successfully reported.");
        get_hazards_list();

      }
    }
    
  }

  const show_visual = async () => {
    if (city_to_show == ""){
      alert("Please Chose a City.")
    }
    else
    {
      let response = await hazardsApi.get_hazards_in_city(city_to_show);
      let hazards_by_city = response.value;
     
      let junctions = []
      hazards_by_city.map((item)=>{
        junctions.push({id:item.id, name: item.type, lat:item.lat, lng:item.lng})
      })
      if (junctions == ""){
        alert("There is no Hazards in "+city_to_show);
      }
      else
      {
        await set_junctions(junctions);
        navigation.navigate('VisualHazards');
      }   
    }
    
    
  }
  


  return (
    <View>
    <ImageBackground source={background} resizeMode="cover">
    <Text style={{fontSize: 30, borderColor: "gray", color:"#841584"}}><b>Hazards List:</b></Text>
    <View style={{display: 'flex', flexDirection:'column'}}>
    <View style={{display: 'flex', flexDirection:'row'}}>
    <ScrollView style={styles.container}>
    <Table columns={columns} data={hazards_list} tableLayout="auto"/>
    </ScrollView>
    <Text>    </Text>    
    <View style={{alignItems: 'center', justifyContent: 'center',border:'red', borderEndColor:'black', borderColor:'black' }}>
      <TextInput
          style={styles.textInputer}
          placeholder="Hazard Location lng"
          onChangeText={newText => setText_to_lng(newText)}
        />
        <TextInput
          style={styles.textInputer}
          placeholder="Hazard Location lat"
          onChangeText={newText => setText_to_lat(newText)}
        />
        <Select
          placeholder="Hazard City"
          options={cities_list}
            onChange={newText => setText_to_city(newText.value)}
        ></Select>
        <Select
          placeholder="Hazard Type"
          options={hazards_types_list}
            onChange={newText => setText_to_type(newText.value)}
        ></Select>
        <TextInput
          style={styles.textInputer}
          placeholder="Hazard Size"
          onChangeText={newText => setText_to_size(newText)}
        />
      <Button onPress={() => add_hazard()} title="Add Hazard" color="#841584"/>


      <Text>  </Text>
      <Text>  </Text>
      <Text>  </Text>

      <Select
        placeholder="Hazard ID to delete"
        options={hazards_ids_list}
        onChange={newText => setText_to_remove_hazard(newText.value)}
      ></Select>
      <Button onPress={() => delete_hazard()} title="Delete Hazard" color="#841584"/>
      <Text>  </Text>
      <Text>  </Text>
      <Select
        placeholder="Hazard ID to Report"
        options={hazards_ids_list}
        onChange={newText => setText_to_report_hazard(newText.value)}
      ></Select>
      <Button onPress={() => report_hazard()} title="Report 'OR Yaruk'" color="green"/>
    
  </View>
  
  </View>
  <View style={{display: 'flex', flexDirection:'row', width:300}}>
  <Select
        placeholder="City"
        options={cities_list}
        onChange={newText => setCity_to_show(newText.value)}
      ></Select>
      <Button onPress={() => show_visual()} title="VISUAL SHOW" color="#841584"/>
  </View>
  </View>
  <Text> </Text>
  <Text> </Text>
  <Text> </Text>
  <Text> </Text>
  <Text> </Text>
  <Text> </Text>
  <Text> </Text>
  <Text> </Text>
  <Text> </Text>
  <Text> </Text>
  </ImageBackground>
  </View>

);



  
}




const columns = [
  {
    title: "ID",
    dataIndex: "id",
    key: "id",
    width: 200,
  },
  {
    title: "Ride ID",
    dataIndex: "ride_id",
    key: "ride_id",
    width: 200,
  },
  {
    title: "Location",
    dataIndex: "location",
    key: "location",
    width: 200,
  },
  {
    title: "City",
    dataIndex: "city",
    key: "city",
    width:200,
  },
  {
    title: "Type",
    dataIndex: "type",
    key: "type",
    width:200,
  },
  {
    title: "Size",
    dataIndex: "size",
    key: "size",
    width:200,
  },
  {
    title: "Hazard Rate",
    dataIndex: "rate",
    key: "rate",
    width:200,
  },
  {
    title: "Reported",
    dataIndex: "report",
    key: "report",
    width:200,
  },
  {
    title: "Photo",
    dataIndex: "",
    key: "photo",
    render: (text, row) => (
      <TouchableOpacity onPress={() => handleViewPhoto(row)} style={{color: 'blue'}}>
        Show
      </TouchableOpacity>      ),
  },
];




const handleViewPhoto = (row) => {
  let base64String = row.photo;
  let byteString = atob(base64String); // decode base64 string to byte string
  let typedArray = new Uint8Array(byteString.length);
  for (let i = 0; i < byteString.length; i++) {
      typedArray[i] = byteString.charCodeAt(i);
  }
  let blob = new Blob([typedArray], { type: "image/jpg" });

  // Create a URL for the Blob
  let url = URL.createObjectURL(blob);

  // Open a new window with the image
  let windowObjectReference = window.open(url, "ImageWindow");

}

const styles = StyleSheet.create({
  container: {
    width:1200,
    height:500,
    padding: 10,
    opacity:0.5,
    backgroundColor:'white'
  },
  hairline: {
    backgroundColor: 'black',
    height: 2,
    width: 200
  },
  textInputer: {
    backgroundColor:'white',
    opacity:0.8,
    textAlign:'center',
    height: 40
  },
  item: {
    padding: 20,
    fontSize: 15,
    marginTop: 5,
  }
});