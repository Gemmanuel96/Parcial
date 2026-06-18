import type { Product } from "../../../types/Product";
import { logout } from "../../../utils/auth";
//Traemos del localStorage el carrito
const carrito = localStorage.getItem("carrito");
const productosCarrito: Product[] = carrito ? JSON.parse(carrito) : [];

console.log(productosCarrito);

// 🔹 Agrupación de productos por id usando reduce
const agrupados: Product[] = agruparProductos(productosCarrito);




//------------------------------------------------------------------------------
// 🔹 Función para mostrar el carrito en pantalla
function mostrarCarrito() {

    // Si el carrito está vacío, mostramos mensaje y ocultamos cajas
    if (productosCarrito.length === 0) {
        const main = document.querySelector<HTMLElement>("#productos-main")
        const mensaje = document.querySelector<HTMLElement>("#mensaje");
        const productoBox = document.querySelector<HTMLElement>("#box1")
        const total = document.querySelector<HTMLElement>("#box2")

        if (mensaje && productoBox && total && main != null) {
            main.innerHTML = "";
            productoBox.style.display = "none";
            total.style.display = "none";
            mensaje.style.display = "block";
            main.appendChild(mensaje);
        }

    } else {
        // Si hay productos, los renderizamos uno por uno
        const carritoBox = document.querySelector<HTMLElement>("#carrito-box");

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

            if (carritoBox)
            carritoBox.appendChild(productoItem);

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



function agruparProductos(productos: Product[]): Product[] {

    const agrupados: Product[] = Object.values(
        productos.reduce((acc: any, prod: Product) => {

            //Sino existe la lista de ese producto id, la crea con cantidad 0
            if (!acc[prod.id]) {
                acc[prod.id] = { ...prod, cantidad: 0 };
            } else {
                //Si existe, suma 1
                acc[prod.id].cantidad += 1;
            }
            return acc;
        }, {})
    );

    return agrupados;
}


const btnSesion = document.querySelector<HTMLButtonElement>("#btn-sesion");

btnSesion?.addEventListener("click", () => {
    logout();
});



// 🔹 Funciones auxiliares para manejar el carrito
function agregarProducto(prod: Product) {
    productosCarrito.push(prod);
    localStorage.setItem("carrito", JSON.stringify(productosCarrito));
}

function eliminarProducto(prod: Product) {
    const index = productosCarrito.findIndex(p => p.id === prod.id);
    if (index !== -1) {
        //  productoCarrito.splice(index, 1);
        localStorage.setItem("carrito", JSON.stringify(productosCarrito));
    }
}

// Devuelve la cantidad de un producto específico
function contadorProductos(id: number): number {
    return productosCarrito.filter(p => p.id === id).length;
}

// 🔹 Función para mostrar subtotal y total
// Calcula el total sumando precios
function calcularTotal(): number {
    let total: number = 0;
    productosCarrito.forEach(p => {
        total += p.precio;
    })
    return total;
}


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
