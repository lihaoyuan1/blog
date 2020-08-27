export default (context) => {
  // eslint-disable-next-line no-undef
  context.$axios.onRequest((config) => {})

  context.$axios.onResponse((res) => {
    return Promise.resolve(res.data)
  })
}
