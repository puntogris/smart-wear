import { Colors } from "@/constants/Colors";
import { Link, Stack } from "expo-router";
import { useEffect, useState } from "react";
import { SafeAreaView, StyleSheet, Text } from "react-native";

export default function Home() {
  const [data, setData] = useState([]);

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

  return (
    <SafeAreaView style={styles.container}>
      <Stack.Screen options={{ headerShown: false }} />
      <Text>{JSON.stringify(data)}</Text>
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
