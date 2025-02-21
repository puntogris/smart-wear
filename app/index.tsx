import { Colors } from "@/constants/Colors";
import { Link, Stack } from "expo-router";
import { useEffect, useState } from "react";
import { SafeAreaView, StyleSheet, Text } from "react-native";
import * as Location from "expo-location";

export default function Home() {
  const [data, setData] = useState([]);
  const [location, setLocation] = useState<Location.LocationObject | null>(
    null
  );

  useEffect(() => {
    fetch("https://smart-wear-sv.vercel.app/api/recs", {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        Authorization: "123",
      },
    })
      .then((res) => res.json())
      .then((data) => setData(data));
  }, []);

  useEffect(() => {
    async function getCurrentLocation() {
      let { status } = await Location.requestForegroundPermissionsAsync();

      if (status !== "granted") {
        console.log("Permission to access location was denied");
        return;
      }

      let location = await Location.getCurrentPositionAsync({});

      const [geocodeResult] = await Location.reverseGeocodeAsync(
        location.coords
      );

      setLocation(location);
    }

    getCurrentLocation();
  }, []);

  return (
    <SafeAreaView style={styles.container}>
      <Stack.Screen options={{ headerShown: false }} />
      <Text>{JSON.stringify(data)}</Text>
      <Text>{JSON.stringify(location)}</Text>
      <Link href="/settings">settings</Link>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: Colors.sky,
  },
});
