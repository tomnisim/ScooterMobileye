import * as React from 'react';
import { View, Text, Button } from 'react-native';
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';

export default function StatisticsWindow({navigation}) {
  return (
    <View style={{alignItems: 'center', justifyContent: 'center' }}>
      <Button onPress={() => navigation.navigate("Home")} title="Home" color="#000000"/>
      <Text>StatisticsWindow Screen</Text>
    </View>
  );
};
    