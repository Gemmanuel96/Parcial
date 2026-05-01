import type { Product } from "../../../types/Product";

// Recuperamos el carrito desde localStorage
const carrito = localStorage.getItem("carrito");
// Si existe, lo parseamos a array de productos; si no, inicializamos vacío
const productoCarrito: Product[] = carrito ? JSON.parse(carrito) : [];

// Contenedor principal donde se mostrarán los productos
const productosBox = document.querySelector<HTMLElement>("#productos-box");
console.log(productosBox);

// 🔹 Agrupación de productos por id usando reduce
const agrupados: Product[] = Object.values(
    productoCarrito.reduce((acc: any, prod: Product) => {
        if (!acc[prod.id]) {
            // Si no existe el producto en el acumulador, lo agregamos con cantidad inicial 0
            acc[prod.id] = { ...prod, cantidad: 0 };
        } else {
            // Si ya existe, incrementamos la cantidad
            acc[prod.id].cantidad += 1;
        }
        return acc;
    }, {})
);

// 🔹 Función para mostrar el carrito en pantalla
function mostrarCarrito() {
    // Si el carrito está vacío, mostramos mensaje y ocultamos cajas
    if (productoCarrito.length === 0) {
        const mensaje = document.querySelector<HTMLElement>("#mensaje");
        const productoBox = document.querySelector<HTMLElement>("#productos-box")
        const total = document.querySelector<HTMLElement>("#total-box")
        if (mensaje && productoBox && total) {
            productoBox.style.display = "none";
            total.style.display = "none";
            mensaje.style.display = "block";
        }
    } else {
        // Si hay productos, los renderizamos uno por uno
        agrupados.forEach(producto => {
            // Contenedor del producto
            const productoItem = document.createElement("div");
            productoItem.classList.add("producto-item")

            // Contenedor de detalle (nombre, descripción, precio)
            const detalle = document.createElement("div");
            detalle.classList.add("detalle-item")

            // Contenedor de controles (+, -, contador)
            const controles = document.createElement("div");
            controles.classList.add("controles-item");

            // Imagen del producto
            const imagen = document.createElement("img");
            imagen.src = producto.imagen
            productoItem.appendChild(imagen);

            // Nombre
            const nombre = document.createElement("h3");
            nombre.textContent = producto.nombre;

            // Descripción
            const descripcion = document.createElement("p");
            descripcion.textContent = producto.descripcion;

            // Precio
            const precio = document.createElement("p");
            precio.style.fontSize = "18px";
            precio.style.fontWeight = "600";
            precio.textContent = "$ " + producto.precio.toString();

            // Armamos detalle
            detalle.appendChild(nombre);
            detalle.appendChild(descripcion);
            detalle.appendChild(precio)

            productoItem.appendChild(detalle);
            productoItem.appendChild(controles);

            productosBox?.appendChild(productoItem);

            // 🔹 Botones de control
            const btnMenos = document.createElement("button");
            btnMenos.textContent = "-";

            const cantidad = document.createElement("span");
            cantidad.textContent = contadorProductos(producto.id).toString();

            const btnMas = document.createElement("button");
            btnMas.textContent = "+";

            controles.appendChild(btnMenos);
            controles.appendChild(cantidad);
            controles.appendChild(btnMas);

            // Eventos de los botones
            btnMas.addEventListener("click", () => {
                agregarProducto(producto); // agrega al carrito
                cantidad.textContent = contadorProductos(producto.id).toString(); // actualiza contador
                console.log("Agregando 1 + de " + producto.nombre);
                mostrarTotal(); // recalcula total
            });

            btnMenos.addEventListener("click", () => {
                eliminarProducto(producto); // elimina del carrito
                cantidad.textContent = contadorProductos(producto.id).toString(); // actualiza contador
                console.log("Eliminando 1 - de " + producto.nombre);
                mostrarTotal(); // recalcula total
            });
        })
    }
}

mostrarCarrito();

// 🔹 Funciones auxiliares para manejar el carrito
function agregarProducto(prod: Product) {
    productoCarrito.push(prod);
    localStorage.setItem("carrito", JSON.stringify(productoCarrito));
}

function eliminarProducto(prod: Product) {
    const index = productoCarrito.findIndex(p => p.id === prod.id);
    if (index !== -1) {
        productoCarrito.splice(index, 1);
        localStorage.setItem("carrito", JSON.stringify(productoCarrito));
    }
}

// Devuelve la cantidad de un producto específico
function contadorProductos(id: number): number {
    return productoCarrito.filter(p => p.id === id).length;
}

// Calcula el total sumando precios
function calcularTotal(): number {
    let total: number = 0;
    productoCarrito.forEach(p => {
        total += p.precio;
    })
    return total;
}

// 🔹 Función para mostrar subtotal y total
function mostrarTotal() {
    const subTotal = document.querySelector<HTMLElement>("#subtotal");
    const total = document.querySelector<HTMLElement>("#total");

    if (subTotal && total) {
        // Limpiamos antes de renderizar
        subTotal.innerHTML = "";
        total.innerHTML = "";

        // Subtotal
        const precio = document.createElement("p");
        const p = document.createElement("p");
        p.textContent = "Subtotal";
        precio.textContent = "$ " + calcularTotal().toString();
        subTotal.appendChild(p);
        subTotal.appendChild(precio);

        // Total
        const precioTotal = document.createElement("p");
        const t = document.createElement("p");
        precioTotal.textContent = "$ " + calcularTotal().toString();
        precioTotal.style.fontWeight = "600";
        t.textContent = "Total";
        t.style.fontWeight = "600";
        t.style.fontSize = "large";
        total.appendChild(t);
        total.appendChild(precioTotal);
    }
}

mostrarTotal();

// 🔹 Botón para vaciar carrito
const btnVaciarCarrito = document.querySelector<HTMLButtonElement>("#btn-vaciar");
btnVaciarCarrito?.addEventListener("click", (Event: MouseEvent) => {
    productoCarrito.length = 0; // vaciamos array
    localStorage.setItem("carrito", JSON.stringify(productoCarrito)); // persistimos vacío
    mostrarCarrito(); // refrescamos vista
    mostrarTotal();   // refrescamos total
})


// 🔹 Botón para finalizar compra
const btnFinalizar = document.querySelector<HTMLButtonElement>("#btn-finalizar");
btnFinalizar?.addEventListener("click", (Event: MouseEvent) => {

    const total = document.querySelector<HTMLElement>("#total-box");
    const confirmacion = document.createElement("div");
    
    confirmacion.classList.add("confirmacion");
    const mensaje = document.createElement("p");
    
    mensaje.textContent = "Compra confirmada!";
    confirmacion.appendChild(mensaje);

    if (total) {
        confirmacion.style.display = "block"
        total.appendChild(confirmacion);
        // Ocultamos el mensaje después de 2 segundos
        setTimeout(() => {
            confirmacion.style.display = "none";
        }, 2000);
    }
})