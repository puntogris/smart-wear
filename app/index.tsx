import { Colors } from "@/constants/Colors";
import { Link, Stack } from "expo-router";
import { useEffect, useState } from "react";
import { SafeAreaView, StyleSheet, Text, View } from "react-native";
import * as Location from "expo-location";

export default function Home() {
  const [data, setData] = useState<string[]>([]);
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
      .then((data) => setData(data.recommendations));
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
      <Text>{JSON.stringify(location)}</Text>
      <ClothingRecs recommendations={data} />
      <Link href="/settings">settings</Link>
    </SafeAreaView>
  );
}

function ClothingRecs({ recommendations }: { recommendations: string[] }) {
  return (
    <View style={styles.recommendation}>
      {recommendations.map((recommendation, index) => (
        <Text key={index} style={styles.recommendationItem}>
          {recommendation}
        </Text>
      ))}
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: Colors.sky,
    paddingStart: 12,
    paddingEnd: 12,
  },
  recommendation: {
    display: "flex",
    flexDirection: "column",
    gap: 10,
    padding: 12,
    backgroundColor: "rgba(0, 0, 0, 0.20)",
    borderRadius: 12,
  },
  recommendationItem: {
    color: "#e1e1e1",
  },
});
