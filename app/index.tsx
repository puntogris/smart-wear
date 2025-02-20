import { Link, Stack, useNavigation } from "expo-router";
import { Text, View } from "react-native";
import { useEffect } from "react";

export default function Home() {
  const navigation = useNavigation();

  useEffect(() => {
    navigation.setOptions({ headerShown: false });
  }, [navigation]);

  return (
    <View style={{ flex: 1 }}>
      <Text>Home Screen</Text>
      <Link href="/settings">settings</Link>
    </View>
  );
}
