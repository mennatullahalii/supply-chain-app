import { useState } from "react";
import axios from "axios";

function App() {
  const [sku, setSku] = useState("MACBOOK-PRO");
  const [quantity, setQuantity] = useState(1);
  const [message, setMessage] = useState("");

  const submitOrder = async () => {
    try {
      const response = await axios.post("http://localhost:8080/api/orders", {
        sku: sku,
        quantity: quantity,
      });
      setMessage(
        `Success! Order #${response.data.id} placed for $${response.data.totalPrice}`,
      );
    } catch (error) {
      setMessage("Failed to place order. Is Kotlin running?");
    }
  };

  return (
    <div style={{ padding: "40px", fontFamily: "sans-serif" }}>
      <h1>Supply Chain App</h1>
      <div style={{ marginBottom: "20px" }}>
        <input
          value={sku}
          onChange={(e) => setSku(e.target.value)}
          placeholder="Product SKU"
          style={{ marginRight: "10px", padding: "5px" }}
        />
        <input
          type="number"
          value={quantity}
          onChange={(e) => setQuantity(Number(e.target.value))}
          style={{ marginRight: "10px", padding: "5px", width: "60px" }}
        />
        <button onClick={submitOrder} style={{ padding: "6px 12px" }}>
          Place Order
        </button>
      </div>
      {message && <p style={{ fontWeight: "bold" }}>{message}</p>}
    </div>
  );
}

export default App;
