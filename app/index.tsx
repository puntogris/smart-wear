import { Colors } from "@/constants/Colors";
import { Link, Stack } from "expo-router";
import { SafeAreaView, StyleSheet, Text } from "react-native";

export default function Home() {
  return (
    <SafeAreaView style={styles.container}>
      <Stack.Screen options={{ headerShown: false }} />
      <Text>Home Screen</Text>
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
